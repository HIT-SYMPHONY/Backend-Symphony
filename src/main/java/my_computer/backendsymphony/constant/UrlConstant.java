package my_computer.backendsymphony.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String CREATE_USER = PRE_FIX;

        private User() {
        }
    }

}