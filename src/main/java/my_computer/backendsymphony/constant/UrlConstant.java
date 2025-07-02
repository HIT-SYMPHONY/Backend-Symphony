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
        public static final String USER_COMMON = PRE_FIX;
        public static final String USER_ID = PRE_FIX+"/{id}";
        public static final String GET_CURRENT_USER=PRE_FIX+"/me";
        public static final String GET_MY_CLASSROOMS=PRE_FIX + "/me" +"/classrooms";
        private User() {
        }
    }

    public static class Lesson {
        private static final String PRE_FIX = "/lesson";
        public static final String CREATE_LESSON = PRE_FIX;
        public static final String DELETE_LESSON = PRE_FIX+"/{lessonId}";
        public static final String UPDATE_LESSON = PRE_FIX+"/{lessonId}";
        private Lesson(){
        }
    }

    public static class Classroom {
        private static final String PRE_FIX = "/classrooms";
        public static final String CREATE_CLASSROOM = PRE_FIX;
        public static final String DELETE_CLASSROOM=PRE_FIX + "/{id}";
        public static final String UPDATE_CLASSROOM=PRE_FIX + "/{id}";
        public static final String GET_CLASSROOM= PRE_FIX + "/{id}";
        public static final String GET_CLASSROOMS=PRE_FIX;
        public static final String ADD_MEMBERS=PRE_FIX + "/{id}" + "/members";
        public static final String GET_MEMBERS=PRE_FIX + "/{id}" + "/members";
        public static final String REMOVE_MEMBERS= PRE_FIX + "/{id}" + "/members";
        private Classroom() {}
    }

    public static class Notification {

        private static final String PRE_FIX = "/notifications";
        public static final String NOTIFICATION_COMMON = PRE_FIX;
        public static final String NOTIFICATION_ID = PRE_FIX + "/{id}";

        public Notification() {}
    }
}