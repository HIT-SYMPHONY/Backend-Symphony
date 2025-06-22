package my_computer.backendsymphony.constant;

public class ErrorMessage {
    private ErrorMessage() {}
    public static final String ERR_EXCEPTION_GENERAL = "Có lỗi bất thường đã xảy ra";
    public static final String UNAUTHORIZED = "Xin lỗi, bạn cần cung cấp thông tin xác thực để truy cập tài nguyên này";
    public static final String ERR_DUPLICATE = "%s với giá trị %s đã tồn tại.";
    //error validation dto
    public static class Validation {
        public static final String NOT_BLANK = "Trường này không thể trống";
        public static final String INVALID_FORMAT_PASSWORD = "Mật khẩu không đủ mạnh (ít nhất 6 ký tự, bao gồm chữ và số)";
        public static final String NOT_NULL= "Trường này là bắt buộc";
        public static final String INVALID_FORMAT_FIELD="Định dạng không hợp lệ";
        public static final String INVALID_DATE_PAST = "Ngày phải ở trong quá khứ";
        public static final String INVALID_STUDENT_CODE="Mã sinh viên phải có 10 ký tự";
    }
    public static class Auth {
        public static final String ERR_INCORRECT_CREDENTIALS = "Mã sinh viên hoặc mật khẩu không chính xác";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_ID = "Không tìm thấy người dùng nào với mã sinh viên: {0}";
        public static final String ERR_NOT_FOUND_STUDENT_CODE = "Không tìm thấy người dùng nào với id: {0}";
    }

}
