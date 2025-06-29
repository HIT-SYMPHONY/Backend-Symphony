package my_computer.backendsymphony.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.ErrorMessage;
import my_computer.backendsymphony.constant.Role;
import my_computer.backendsymphony.domain.dto.pagination.PaginationRequestDto;
import my_computer.backendsymphony.domain.dto.pagination.PaginationResponseDto;
import my_computer.backendsymphony.domain.dto.pagination.PagingMeta;
import my_computer.backendsymphony.domain.dto.request.ClassroomCreationRequest;
import my_computer.backendsymphony.domain.dto.request.ClassroomUpdateRequest;
import my_computer.backendsymphony.domain.dto.response.ClassroomResponse;
import my_computer.backendsymphony.domain.entity.ClassRoom;
import my_computer.backendsymphony.domain.entity.User;
import my_computer.backendsymphony.domain.mapper.ClassroomMapper;
import my_computer.backendsymphony.exception.DuplicateResourceException;
import my_computer.backendsymphony.exception.InvalidException;
import my_computer.backendsymphony.exception.NotFoundException;
import my_computer.backendsymphony.repository.ClassroomRepository;
import my_computer.backendsymphony.repository.UserRepository;
import my_computer.backendsymphony.service.ClassroomService;
import my_computer.backendsymphony.util.PaginationUtil;
import my_computer.backendsymphony.util.UploadFileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    ClassroomRepository classroomRepository;
    UserRepository userRepository;
    ClassroomMapper classroomMapper;
    UploadFileUtil uploadFileUtil;

    @Override
    public ClassroomResponse createClassroom(ClassroomCreationRequest request, MultipartFile imageFile) {
        if (classroomRepository.existsByName(request.getName()))
            throw new DuplicateResourceException(
                    ErrorMessage.ERR_DUPLICATE,
                    new String[]{"Tên lớp", request.getName()}
            );
        User leader = userRepository.findById(request.getLeaderId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{request.getLeaderId()}));
        if (leader.getRole() != Role.LEADER)
            throw new InvalidException(ErrorMessage.Classroom.USER_IS_NOT_LEADER);
        ClassRoom classRoom = classroomMapper.toClassRoom(request);
        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            classRoom.setImage(imageUrl);
        }
        ClassRoom savedClassroom = classroomRepository.save(classRoom);
        ClassroomResponse response = classroomMapper.toClassroomResponse(savedClassroom);
        response.setLeaderName(leader.getFullName());
        return response;
    }

    @Override
    public void deleteClassroom(String id) {
        ClassRoom classroomToDelete = findClassroomByIdOrElseThrow(id);
        for (User member : classroomToDelete.getMembers()) {
            member.getClassRooms().remove(classroomToDelete);
        }
        classroomRepository.delete(classroomToDelete);
    }

    @Override
    @Transactional
    public ClassroomResponse updateClassroom(String id, ClassroomUpdateRequest request, MultipartFile imageFile) {
        ClassRoom existingClassroom = findClassroomByIdOrElseThrow(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isLeaderOfClassroom(existingClassroom, authentication))
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        if (request.getName() != null) {
            if (request.getName().isBlank()) {
                throw new InvalidException(ErrorMessage.Classroom.NAME_CANNOT_BE_BLANK);
            }
            //request class name already exists
            if (!existingClassroom.getName().equals(request.getName()) && classroomRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Tên lớp học", request.getName()});
            }
        }
        User finalLeader;
        //only update leaderId if it's new
        if (request.getLeaderId() != null && !request.getLeaderId().equals(existingClassroom.getLeaderId())) {
            User newLeader = findUserByIdOrElseThrow(request.getLeaderId());
            if (newLeader.getRole() != Role.LEADER)
                throw new InvalidException(ErrorMessage.Classroom.USER_IS_NOT_LEADER);
            finalLeader = newLeader;
        } else {
            // get current leader to get leader name
            finalLeader = findUserByIdOrElseThrow(existingClassroom.getLeaderId());
        }

        classroomMapper.updateClassroom(request, existingClassroom);

        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            if (existingClassroom.getImage() != null) {
                uploadFileUtil.destroyImage(existingClassroom.getImage());
            }
            String newImageUrl = uploadFileUtil.uploadImage(imageFile);
            existingClassroom.setImage(newImageUrl);
        }

        ClassroomResponse response = classroomMapper.toClassroomResponse(existingClassroom);
        response.setLeaderName(finalLeader.getFullName());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomResponse getClassroomById(String id) {
        ClassRoom classRoom = findClassroomByIdOrElseThrow(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isValidLeader = isLeaderOfClassroom(classRoom, authentication);
        boolean isValidMember = isMemberOfClassroom(classRoom, authentication);
        if (!isValidLeader && !isValidMember)
            throw new AccessDeniedException(ErrorMessage.FORBIDDEN);
        User leader = findUserByIdOrElseThrow(classRoom.getLeaderId());
        ClassroomResponse response = classroomMapper.toClassroomResponse(classRoom);
        response.setLeaderName(leader.getFullName());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<ClassroomResponse> getAllClassrooms(PaginationRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<ClassRoom> classroomPage = classroomRepository.findAll(pageable);

        List<ClassroomResponse> classroomResponses = classroomMapper.toClassroomResponseList(classroomPage.getContent());

        if (!classroomResponses.isEmpty()) {
            List<String> leaderIds = classroomResponses.stream()
                    .map(ClassroomResponse::getLeaderId)
                    .distinct()
                    .collect(Collectors.toList());

            Map<String, User> leaderMap = userRepository.findAllById(leaderIds).stream()
                    .collect(Collectors.toMap(User::getId, user -> user));

            classroomResponses.forEach(response -> {
                User leader = leaderMap.get(response.getLeaderId());
                if (leader != null) {
                    response.setLeaderName(leader.getFullName());
                }
            });
        }

        PagingMeta meta = PaginationUtil.buildPagingMeta(request, classroomPage);
        return new PaginationResponseDto<>(meta, classroomResponses);
    }


    private boolean isLeaderOfClassroom(ClassRoom classroom, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        return currentUserId.equals(classroom.getLeaderId());
    }

    private boolean isMemberOfClassroom(ClassRoom classRoom, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String role = jwt.getClaimAsString("scope");
        if (role.equals(Role.ADMIN.name())) return true;
        String currentUserId = jwt.getSubject();
        for (User member : classRoom.getMembers()) {
            if (currentUserId.equals(member.getId())) return true;
        }
        return false;
    }

    private ClassRoom findClassroomByIdOrElseThrow(String id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.Classroom.ERR_NOT_FOUND_ID,
                        new String[]{id}
                ));
    }

    private User findUserByIdOrElseThrow(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}
                ));
    }
}
