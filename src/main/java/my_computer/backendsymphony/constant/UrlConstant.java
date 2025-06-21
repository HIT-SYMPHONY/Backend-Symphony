package my_computer.backendsymphony.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String CREATE_USER = PRE_FIX;
        public static final String GET_USER=PRE_FIX+"/{id}";
        private User() {
        }
    }

}