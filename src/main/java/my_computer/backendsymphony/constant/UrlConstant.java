package my_computer.backendsymphony.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String FORGOT_PASSWORD = PRE_FIX + "/forgot-password";
        public static final String VERIFY_TEMPPASSWORD = PRE_FIX + "/temp-password";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";
        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String USER_COMMON = PRE_FIX;
        public static final String USER_ID = PRE_FIX+"/{id}";
        public static final String GET_CURRENT_USER=PRE_FIX+"/me";
        public static final String GET_MY_CLASSROOMS=GET_CURRENT_USER +"/classrooms";
        public static final String GET_MY_COMPETITIONS=GET_CURRENT_USER +"/competitions";
        private User() {
        }
    }

    public static class Lesson {
        private static final String PRE_FIX = "/lesson";
        public static final String CREATE_LESSON = PRE_FIX;
        public static final String DELETE_LESSON = PRE_FIX+"/{lessonId}";
        public static final String UPDATE_LESSON = PRE_FIX+"/{lessonId}";
        public static final String GET_LESSON_BY_CLASSROOM_ID = PRE_FIX + "/classroom/{classroomId}";
        public static final String GET_LESSON_BY_CURRENT_USER_ID = PRE_FIX + "/my-lessons";
        private Lesson(){
        }
    }

    public static class Classroom {
        private static final String PRE_FIX = "/classrooms";
        public static final String CLASSROOM_COMMON = PRE_FIX;
        public static final String CLASSROOM_ID = PRE_FIX + "/{id}";
        public static final String MEMBERS=PRE_FIX + "/{id}" + "/members";
        public static final String CLASSROOM_NAME=PRE_FIX +"/search" + "/{name}" + "/members";
        private Classroom() {}
    }

    public static class Notification {

        private static final String PRE_FIX = "/notifications";
        public static final String NOTIFICATION_COMMON = PRE_FIX;
        public static final String NOTIFICATION_ID = PRE_FIX + "/{id}";
        public Notification() {}
    }

    public static class Post {
        private static final String PRE_FIX = "/posts";
        public static final String POST_COMMON = PRE_FIX;
        public static final String POST_ID = PRE_FIX + "/{id}";
        public static final String POST_POST_ID = PRE_FIX + "/{postId}/get";

    }

    public static class Competition {
        private static final String PRE_FIX = "/competitions";
        public static final String COMPETITION_COMMON = PRE_FIX;
        public static final String COMPETITION_ID = PRE_FIX + "/{id}";
        private Competition() {}
    }

    public static class CommentPost {
        private static final String PRE_FIX = "/comment-posts";
        public static final String COMMENT_POST_COMMON = PRE_FIX;
        public static final String COMMENT_POST_ID = PRE_FIX + "/{id}";
    }
}