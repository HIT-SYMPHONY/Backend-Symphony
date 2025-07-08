package my_computer.backendsymphony.constant;

public class ErrorMessage {

    private ErrorMessage() {}
    public static final String ERR_EXCEPTION_GENERAL = "Có lỗi bất thường đã xảy ra";
    public static final String UNAUTHORIZED = "Xin lỗi, bạn cần cung cấp thông tin xác thực để thực hiện hành động này";
    public static final String ERR_DUPLICATE = "%s với giá trị %s đã tồn tại.";
    public static final String FORBIDDEN = "Xin lỗi, bạn không có quyền để thực hiện hành động này";
    public static final String INVALID_IMAGE_FILE="Chỉ cho phép hình ảnh PNG, JPG, JPEG, WEBP hoặc GIF";
    public static final String INVALID_JSON_FORMAT = "Dữ liệu gửi lên có định dạng JSON không hợp lệ. Vui lòng kiểm tra lại.";
    //error validation dto
    public static class Validation {
        public static final String NOT_BLANK = "Trường này không thể trống";
        public static final String INVALID_FORMAT_PASSWORD = "Mật khẩu không đủ mạnh (ít nhất 6 ký tự, bao gồm chữ và số)";
        public static final String NOT_NULL= "Trường này là bắt buộc";
        public static final String NOT_EMPTY="Trường này không được để rỗng";
        public static final String INVALID_FORMAT_FIELD="Định dạng không hợp lệ";
        public static final String MUST_IN_PAST = "Ngày phải ở trong quá khứ";
        public static final String MUST_IN_FUTURE = "Ngày phải ở trong tương lai";
        public static final String INVALID_STUDENT_CODE="Mã sinh viên phải có 10 ký tự";
        public static final String POSITIVE="Số nhập vào phải >0";
    }
    public static class Auth {
        public static final String ERR_INCORRECT_CREDENTIALS = "Mã sinh viên hoặc mật khẩu không chính xác";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_ID = "Không tìm thấy người dùng nào với id: %s";
        public static final String ERR_NOT_FOUND_STUDENT_CODE = "Không tìm thấy người dùng nào với mã sinh viên: %s";
        public static final String ERR_NOT_FOUND_ONE_OR_MORE_IDS="Không tìm thấy một hoặc nhiều id người dùng được cung cấp";
        public static final String USER_IS_NOT_LEADER="Người dùng phải không phải leader";
    }

    public static class Classroom {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy lớp học nào với id: %s";
        public static final String NAME_CANNOT_BE_BLANK = "Tên lớp học không được để trống.";
        public static final String CLASS_LEADER_CANNOT_BE_MEMBER="Leader của lớp học không thể được thêm vào thành viên bình thường";
    }

    public static class Notification {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy thông báo.";

    }

    public static class Competition {
        public static final String ERR_NOT_FOUND_ID= "Không tìm thấy cuộc thi nào với id: %s";
        public static final String START_TIME_MUST_BEFORE_END_TIME="Thời gian bắt đầu phải trước thời gian kết thúc";
    }


    public static final String EmailNotFound = "Email not found!";
}
