package my_computer.backendsymphony.constant;

public class ErrorMessage {


    private ErrorMessage() {}
    public static final String ERR_EXCEPTION_GENERAL = "Có lỗi bất thường đã xảy ra";
    public static final String UNAUTHORIZED = "Xin lỗi, bạn cần cung cấp thông tin xác thực để thực hiện hành động này";
    public static final String ERR_DUPLICATE = "%s với giá trị %s đã tồn tại.";
    public static final String FORBIDDEN = "Xin lỗi, bạn không có quyền để thực hiện hành động này";
    public static final String INVALID_IMAGE_FILE="Chỉ cho phép hình ảnh PNG, JPG, JPEG, WEBP hoặc GIF";
    public static final String INVALID_JSON_FORMAT = "Dữ liệu gửi lên có định dạng JSON không hợp lệ. Vui lòng kiểm tra lại.";
    public static final String TO_MANY_REQUEST = "Bạn đã gửi quá nhiều yêu cầu. Vui lòng thử lại sau.";
    public static final String INCORRECT_PASSWORD = "Mật khẩu sai";
    public static final String NOT_FOUND = "Không tìm thấy tài nguyên";
    public static final String INVALID_SORT_FIELD = "Trường sắp xếp '%s' không hợp lệ. Các trường có thể được sắp xếp là: %s.";
    public static final String HTTP_METHOD_NOT_SUPPORTED = "Phương thức HTTP này không được hỗ trợ";
    //error validation dto
    public static class Validation {
        public static final String NOT_BLANK = "Không thể trống";
        public static final String INVALID_FORMAT_PASSWORD = "Mật khẩu không đủ mạnh (ít nhất 6 ký tự, bao gồm chữ và số)";
        public static final String NOT_NULL= "Trường này là bắt buộc";
        public static final String NOT_EMPTY="Trường này không được để rỗng";
        public static final String INVALID_FORMAT_FIELD="Định dạng không hợp lệ";
        public static final String MUST_IN_PAST = "Ngày phải ở trong quá khứ";
        public static final String MUST_IN_FUTURE = "Ngày phải ở trong tương lai";
        public static final String INVALID_STUDENT_CODE="Mã sinh viên phải có 10 ký tự";
        public static final String POSITIVE="Số nhập vào phải >0";
        public static final String INVALID_SCORE="Điểm phải lớn hơn hoặc bằng 0 và nhỏ hơn hoặc bằng 10";
        public static final String INVALID_ENUM_VALUE = "Giá trị '%s' không hợp lệ. Các giá trị được chấp nhận là: %s";
        public static final String INVALID_TYPE_VALUE = "Giá trị '%s' không hợp lệ cho trường này.";
        public static final String MUST_BE_JSON_STRING = "Trường này phải là một chuỗi JSON hợp lệ.";
    }
    public static class Auth {
        public static final String ERR_INCORRECT_CREDENTIALS = "Mã sinh viên hoặc mật khẩu không chính xác";
        public static final String ERR_INVALID_REFRESH_TOKEN = "Mã sinh viên hoặc mật khẩu không chính xác";

    }

    public static class User {
        public static final String ERR_NOT_FOUND_ID = "Không tìm thấy người dùng nào với id: %s";
        public static final String NOT_FOUND_ONE_OR_MORE = "Không tìm thấy người dùng nào với id: %s";
        public static final String ERR_NOT_FOUND_STUDENT_CODE = "Không tìm thấy người dùng nào với mã sinh viên: %s";
        public static final String ERR_NOT_FOUND_ONE_OR_MORE_IDS="Không tìm thấy một hoặc nhiều id người dùng được cung cấp";
        public static final String USER_IS_NOT_LEADER="Người dùng phải không phải leader";
        public static final String USERNAME_NOT_FOUND="Không tìm thấy người dùng nào với username" ;
        public static final String INVALID_ROLE = "Vai trò không hợp lệ";
        public static final String ILLEGAL = "Bạn không thể tự thay đổi quyền của chính mình xuống thấp hơn";
    }

    public static class Classroom {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy lớp học nào với id: %s";
        public static final String NAME_CANNOT_BE_BLANK = "Tên lớp học không được để trống.";
        public static final String CLASS_LEADER_CANNOT_BE_MEMBER="Leader của lớp học không thể được thêm vào thành viên bình thường";
    }

    public static class Notification {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy thông báo.";
        public static final String ILLEGAL= "Notification chỉ gắn với một trong hai: classRoom hoặc competition.";

    }

    public static class Post {
        public static final String ERR_NOT_FOUND_ID = "Không tìm thấy bài tập nào với id: %s";
    }

    public static class CommentPost {
        public static final String ERR_NOT_FOUND_ID = "Không tìm thấy bài nộp nào của bài tập với id: %s";
        public static final String ERR_ALREADY_COMMENT = "Một user chỉ được comment một lần trên một bài";
    }

    public static class Competition {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy cuộc thi nào với id: %s";
        public static final String START_TIME_MUST_BEFORE_END_TIME="Thời gian bắt đầu phải trước thời gian kết thúc";
        public static final String INVALID_TIME_PERIOD = "Không phải thời gian diễn ra";
    }

    public static class CommentCompetition {
        public static final String ERR_NOT_FOUND_ID= "Không tìm phần trả lời nào cuộc thi nào với id: %s";
        public static final String CANNOT_COMMENT_BEFORE_REGISTER = "Không thể trả lời trước khi đăng kí";
    }

    public static class CompetitionUser {
        public static final String ALREADY_JOINED = "Người dùng đã tham gia cuộc thi, không thể tham gia lại";
        public static final String ERR_NOT_FOUND = "Không thể tìm thấy người dùng này trong cuộc thi này";
        public static final String AFTER_REGISTER_PERIOD = "Thời gian đăng kí cuộc thi đã hết";
    }

    public static class Lesson {
        public static final String START_TIME_MUST_BEFORE_END_TIME="Thời gian bắt đầu phải trước thời gian kết thúc";
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy buổi học nào với id: %s";
    }

    public static class File {
        public static final String FILE_IS_EMPTY = "Tệp không được để trống hoặc không được cung cấp.";
        public static final String INVALID_IMAGE_TYPE = "Định dạng tệp không hợp lệ. Chỉ cho phép các tệp hình ảnh (png, jpg, jpeg, gif...).";
        public static final String FILE_TOO_LARGE = "Tệp quá lớn. Kích thước tối đa cho phép là 5MB.";
        public static final String UPLOAD_FAILED = "Tải tệp lên không thành công. Vui lòng thử lại.";
        public static final String DESTROY_FAILED = "Xóa tệp không thành công.";
        public static final String INVALID_CLOUDINARY_URL = "URL hình ảnh Cloudinary không hợp lệ.";
    }



    public static final String EmailNotFound = "Email not found!";
}
