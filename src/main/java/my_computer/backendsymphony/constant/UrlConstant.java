package my_computer.backendsymphony.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String FORGOT_PASSWORD = PRE_FIX + "/forgot-password";
        public static final String VERIFY_TEMPPASSWORD = PRE_FIX + "/verify-temppassword";
        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String CREATE_USER = PRE_FIX;
        public static final String GET_USER=PRE_FIX+"/{id}";
        public static final String GET_CURRENT_USER=PRE_FIX+"/me";
        public static final String UPDATE_USER = PRE_FIX+"/{id}";
        public static final String DELETE_USER = PRE_FIX+"/{id}";
        private User() {
        }
    }

    public static class Classroom {
        private static final String PRE_FIX = "/classrooms";
        public static final String CREATE_CLASSROOM = PRE_FIX;
        public static final String DELETE_CLASROOM=PRE_FIX + "/{id}";

        private Classroom() {}
    }

    public static class Notification {

        private static final String PRE_FIX = "/notifications";
        public static final String NOTIFICATION_COMMON = PRE_FIX;

        public Notification() {}
    }
}