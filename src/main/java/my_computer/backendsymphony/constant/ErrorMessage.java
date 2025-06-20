package my_computer.backendsymphony.constant;

public class ErrorMessage {

    public static final String ERR_EXCEPTION_GENERAL = "exception.general";
    public static final String UNAUTHORIZED = "exception.unauthorized";
    public static final String DUPLICATE_RESOURCE = "exception.duplicate.resource";
    //error validation dto
    public static class Validation {
        public static final String NOT_BLANK = "{invalid.general.not-blank}";
        public static final String INVALID_FORMAT_PASSWORD = "{invalid.password-format}";
        public static final String NOT_NULL= "{invalid.general.not-null}";
        public static final String INVALID_FORMAT_FIELD="{invalid.general.format}";
        public static final String INVALID_DATE_PAST = "{invalid.date-past}";
        public static final String INVALID_STUDENT_CODE="{invalid.student-code}";
    }
    public static class Auth {
        public static final String ERR_INCORRECT_USERNAME = "exception.auth.incorrect.username";
        public static final String ERR_INCORRECT_PASSWORD = "exception.auth.incorrect.password";
    }

    public static class User {
        public static final String ERR_NOT_FOUND_USERNAME = "exception.user.not.found.username";
        public static final String ERR_NOT_FOUND_ID = "exception.user.not.found.id";
    }

}
