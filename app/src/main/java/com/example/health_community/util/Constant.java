package com.example.health_community.util;

import android.os.Environment;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhang on 2016.09.23.
 */
public class Constant {

    public static final String SP_KEY="com.example.databaseproject";
    public static final String BOOK_SHELF = "book_shelf";
    public static final String IMAGE_URL="http://p5zjyajpj.bkt.clouddn.com/";

    public static final String SEARCH_LIST = "bookList_search";
    public static final String BOOK = "book";
    public static final String BORROW_BOOK = "borrow_book";
    public static final String RECYCLER_LIST = "recycler_list";
    public static final String BORROW_LIST = "borrow_list";
    public static final String BORROW_RECORD_LIST = "borrow_record_list";
    public static final String RETURN_RECORD_LIST = "return_record_list";
    public static final String VIOLATION_RECORD_LIST = "violation_record_list";
    public static final String HAVENT_BORROW_BOOK = "havent_borrow_book";
    public static final String HAVE_BORROW_BOOK = "have_borrow_book";
    public static final String BOOK_DETAIL = "book_list_detail";
    public static final String BOOK_DETAIL_UPDATE = "book_update";
    public static final String MEDINCINE_INFO = "medicine_info";

    public static final String THEME_MODEL = "theme_model";
    public static final String USER_GENDER = "user_gender";

    public static String APP_URL = "https://play.google.com/store/apps/details?id=com.eajy.materialdesigndemo";
    public static String DESIGNED_BY = "Designed by Eajy in China";
    public static String SHARE_CONTENT = "A beautiful app designed with Material Design:\n" + APP_URL + "\n- " + DESIGNED_BY;
    public static String EMAIL = "mailto:eajy.zhangxiao@gmail.com";
    public static String GIT_HUB = "https://github.com/Eajy/MaterialDesignDemo";

    public static String MATERIAL_DESIGN_COLOR_URL = "https://play.google.com/store/apps/details?id=com.eajy.materialdesigncolor";
    public static String MATERIAL_DESIGN_COLOR_PACKAGE = "com.eajy.materialdesigncolor";

    public static final String REGISTER_SUCCESS = "Register Success";
    public static final String LOGIN_SUCCESS = "Login Success";
    public static final String ADMIN = "admin";
    public static final String ADMIN_ID = "adminID";
    public static final String NEW_USER = "new_user";
    public static final String OLD_USER = "old_user";
    public static final String USER_NAME = "user_name";
    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_PASSWORD = "user_pass";
    public static final String USER_ID = "user_id";
    public static final String USER_STATUS = "userStatus";
    public static final String USER_STATUS_1 = "login";
    public static final String USER_STATUS_2 = "register";
    public static final String USER_STATUS_3 = "offline";


    /**
     * category index
     */
    public static final int CATEGORY_LITERATURE = 0;
    public static final int CATEGORY_POPULAR = 1;
    public static final int CATEGORY_CULTURE = 2;
    public static final int CATEGORY_LIFE = 3;
    public static final int CATEGORY_MANAGEMENT = 4;
    public static final int CATEGORY_TECHNOLOGY = 5;
    public static final int CATEGORY_COUNTRY = 6;
    public static final int CATEGORY_SUBJECT = 7;
    public static final int CATEGORY_AUTHOR = 8;
    public static final int CATEGORY_PUBLISHER = 9;
    public static final int CATEGORY_THRONG = 10;
    public static final int CATEGORY_RELIGION = 11;
    public static final int CATEGORY_OTHER = 12;

    public static boolean isNetWork =false;
    /**
     * Unknown network class
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * wifi net work
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * "2G" networks
     */
    public static final int NETWORK_CLASS_2_G = 2;

    /**
     * "3G" networks
     */
    public static final int NETWORK_CLASS_3_G = 3;

    /**
     * "4G" networks
     */
    public static final int NETWORK_CLASS_4_G = 4;

    public static boolean NET_FLAG=false;
    public static final String BTF_DATA = "com.example.health_community.data";
    public static final String APP_VERSION = "btfrg_V1.2.apk";
    public static final String IMG_EXIST = "IMG_EXIST ";
    public static final String DB_EXIST = "DB_EXIST ";
    public static final String OLD_DATA = "old_data";
    public static final String DATA_EXIST = "DATA_EXIST";
    public static final String IMAGE_LIST = "imageList";
    public static final String BTF_ID = "butterflyNo";
    public static final String HISTORY_RECORD = "history_record";

    public static final int CHOOSE_PHOTO = 2;
    public static final int CROP_PHOTO_FORCAMERA = 5;
    public static final int OVERLAY_PERMISSION_REQ_CODE = 1234;
    public static final String TAKE_PHOTO_PATH = "TAKE_PHOTO";

    public static final String INSTALL_PATH= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/";
    public static final String _URL = "https://scau935.fashcollege.com";
    public static final String UPDATE_URL = "http://scau935.fashcollege.com/apk/";
    public static final String INFO_URL="http://scau935.fashcollege.com/getInfo.do";
    public static final String LOGIN_URL = "http://192.168.137.1:8080/JSoupDemo/user/loginUser";
    public static final String REGISTER_URL = "http://192.168.137.1:8080/JSoupDemo/user/registerUser";
    public static final String IDENTIFY_URL="http://40.125.207.182:8080/identify.do";
    public static final String CHECK_UPDATE_URL = "http://40.125.207.182:8080/getFileName.do";
    public static String GET_NEWS_URL = "https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=";
    public static String SEARCH_MEDICINE_URL = "https://pydwp.xyz/JSoupDemo/medicine/getJsonMedicines.do?searchContent=";

    //    public static final String _URL = "http://40.125.207.182:8080";
    //    public static final String UPDATE_URL = "http://40.125.207.182:8080/apk/";
    //    public static final String INFO_URL="http://40.125.207.182:8080/getInfo.do";
    //    public static final String IDENTIFY_URL="http://40.125.207.182:8080/identify.do";
    //    public static final String CHECK_UPDATE_URL = "http://40.125.207.182:8080/getFileName.do";


    //最热排行
    public static final String EBOOK_RANK_ID_HOT_MALE = "54d42d92321052167dfb75e3";
    public static final String EBOOK_RANK_ID_HOT_FEMALE = "54d43437d47d13ff21cad58b";
    //留存排行
    public static final String EBOOK_RANK_ID_RETAINED_MALE = "564547c694f1c6a144ec979b";
    public static final String EBOOK_RANK_ID_RETAINED_FEMALE = "5645482405b052fe70aeb1b5";
    //完结排行
    public static final String EBOOK_RANK_ID_FINISHED_MALE = "564eb878efe5b8e745508fde";
    public static final String EBOOK_RANK_ID_FINISHED_FEMALE = "564eb8a9cf77e9b25056162d";
    //潜力排行
    public static final String EBOOK_RANK_ID_POTENTIAL_MALE = "564547c694f1c6a144ec979b";
    public static final String EBOOK_RANK_ID_POTENTIAL_FEMALE = "5645482405b052fe70aeb1b5";
    //rank type
    public static final int TYPE_HOT_RANKING = 0;
    public static final int TYPE_RETAINED_RANKING = 1;
    public static final int TYPE_FINISHED_RANKING = 2;
    public static final int TYPE_POTENTIAL_RANKING = 3;
    //图书分类过滤
    //hot(热门)、new(新书)、reputation(好评)、over(完结)
    public static final String EBOOK_FILTER_HOT = "hot";
    public static final String EBOOK_FILTER_NEW = "new";
    public static final String EBOOK_FILTER_REPUTATION = "reputation";
    public static final String EBOOK_FILTER_OVER = "over";

    //图书评论分类
    //updated(默认排序)、created(最新发布)、helpful(最有用的)、comment-count(最多评论)
    public static final String EBOOK_SORT_UPDATED = "updated";
    public static final String EBOOK_SORT_CREATED = "created";
    public static final String EBOOK_SORT_HELPFUL = "helpful";
    public static final String EBOOK_SORT_COMMENT_COUNT = "comment-count";

    //activity result code
    public static final int BOOK_READER_RESULT = 0x0001;
    public static final int BOOK_READER_RESULT_OK = 0;
    public static final int BOOK_READER_RESULT_FAILED = 1;

    @StringDef({
            Constant.Gender.MALE,
            Constant.Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

}
