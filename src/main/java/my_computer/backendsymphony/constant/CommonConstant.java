package my_computer.backendsymphony.constant;

import java.util.List;

public class CommonConstant {
    public static final String BEARER_TOKEN = "Bearer";

    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    public static final String EMPTY_STRING = "";
    public static final String SORT_TYPE_ASC = "ASC";
    public static final String SORT_TYPE_DESC = "DESC";
    public static final long MAX_IMAGE_SIZE_BYTES = 5 * 1024 * 1024;
    public static final List<String> ALLOWED_IMAGE_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp");

}
