package my_computer.backendsymphony.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";
        public static final String LOGIN = PRE_FIX + "/login";
        public static final String FORGOT_PASSWORD = PRE_FIX + "/forgot-password";
        public static final String VERIFY_TEMPPASSWORD = PRE_FIX + "/temp-password";
        public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password";
        public static final String VERIFY_PASSWORD = PRE_FIX + "/verify-password";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/users";
        public static final String USER_COMMON = PRE_FIX;
        public static final String USER_ID = PRE_FIX + "/{id}";
        public static final String UPDATE_ROLE = PRE_FIX + "/role";
        public static final String GET_CURRENT_USER = PRE_FIX + "/me";
        public static final String GET_MY_CLASSROOMS = GET_CURRENT_USER + "/classrooms";
        public static final String GET_MY_COMPETITIONS = GET_CURRENT_USER + "/competitions";
        public static final String GET_LEADERS = PRE_FIX + "/leaders";
        public static final String GET_BY_USERNAME = PRE_FIX + "/find";
        public static final String GET_CLASS_USER = USER_ID + "/classrooms";
        public static final String GET_MY_POSTS = GET_CURRENT_USER + "/posts";
        public static final String RESET_PASSWORD = USER_ID + "/reset-password";


        private User() {
        }
    }

    public static class Lesson {
        private static final String PRE_FIX = "/lesson";
        public static final String CREATE_LESSON = PRE_FIX;
        public static final String GET_LESSON_BY_ID = PRE_FIX + "/{lessonId}";
        public static final String DELETE_LESSON = PRE_FIX + "/{lessonId}";
        public static final String UPDATE_LESSON = PRE_FIX + "/{lessonId}";
        public static final String GET_LESSON_BY_CLASSROOM_ID = PRE_FIX + "/classroom/{classroomId}";
        public static final String GET_LESSON_BY_CURRENT_USER_ID = PRE_FIX + "/my-lessons";

        private Lesson() {
        }
    }

    public static class Classroom {
        private static final String PRE_FIX = "/classrooms";
        public static final String CLASSROOM_COMMON = PRE_FIX;
        public static final String CLASSROOM_ID = PRE_FIX + "/{id}";
        public static final String MEMBERS = PRE_FIX + "/{id}" + "/members";
        public static final String NON_MEMBERS = PRE_FIX + "/{id}/non-members";
        public static final String CLASSROOM_NAME = PRE_FIX + "/search" + "/{name}" + "/members";
        public static final String BY_LEADER = PRE_FIX + "/by-leader";
        public static final String GET_CLASSROOM_NOTIFICATIONS = CLASSROOM_ID + "/notifications";
        private Classroom() {
        }
    }

    public static class Notification {

        private static final String PRE_FIX = "/notifications";
        public static final String NOTIFICATION_COMMON = PRE_FIX;
        public static final String NOTIFICATION_ID = PRE_FIX + "/{id}";
        public static final String  GET_MY_NOTIFICATIONS = PRE_FIX + "/me";
        private Notification() {
        }
    }

    public static class Post {
        private static final String PRE_FIX = "/posts";
        public static final String POST_COMMON = PRE_FIX;
        public static final String POST_ID = PRE_FIX + "/{id}";
        public static final String GET_POSTS_BY_CLASSROOM_ID = Classroom.CLASSROOM_ID + "/posts";
    }

    public static class Competition {
        private static final String PRE_FIX = "/competitions";
        public static final String COMPETITION_COMMON = PRE_FIX;
        public static final String COMPETITION_ID = PRE_FIX + "/{id}";
        public static final String GET_COMPETITION_NOTIFICATIONS= COMPETITION_ID + "/notifications";
        private Competition() {
        }
    }

    public static class CommentCompetition {
        private static final String PRE_FIX = "/competition-comments";
        public static final String COMMENT_COMPETITION_ID = PRE_FIX + "/{id}";
        public static final String COMMENT_COMPETITION_SCORE = PRE_FIX + "/{id}";
        public static final String GET_COMPETITION_COMMENTS = Competition.COMPETITION_ID + "/comments";
        public static final String GET_MY_COMPETITION_COMMENTS = GET_COMPETITION_COMMENTS + "/me";
        public static final String COMMENT_COMPETITION_CONTENT = PRE_FIX + "/{id}/content";
    }

    public static class CommentPost {
        private static final String PRE_FIX = "/post-comments";
        public static final String COMMENT_POST_ID = PRE_FIX + "/{id}";
        public static final String GET_POST_COMMENTS = Post.POST_ID + "/comments";
        public static final String GET_MY_POST_COMMENTS = GET_POST_COMMENTS + "/me";
    }

    public static class CompetitionUser {
        private static final String PRE_FIX = "/competition-users";
        public static final String COMPETITION_USER_COMMON = Competition.COMPETITION_ID + "/users";
        public static final String COMPETITION_USER_ID = COMPETITION_USER_COMMON + "/{userId}";
        public static final String ADD_MULTIPLE = PRE_FIX + "/add-multiple";
        public static final String REMOVE_MULTIPLE = PRE_FIX + "/remove-multiple";
        public static final String MEMBERS = PRE_FIX + "/{id}" + "/members";
        public static final String NON_MEMBERS = PRE_FIX + "/{id}" + "/non-members";

    }

    public static final class Image {
        public static final String PRE_FIX = "/images";
        public static final String IMAGE_COMMON=PRE_FIX;
    }

    public static class Websocket {
        private static final String PRE_FIX = "/topic";
        public static final String NOTIFICATION_CLASSROOM = PRE_FIX + "/classrooms/%s/notifications";
        public static final String NOTIFICATION_COMPETITION = PRE_FIX + "/competitions/%s/notifications";
    }

}
