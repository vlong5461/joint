package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UserProfileBean extends BaseBean{
    private static final long serialVersionUID = 7496511477437308864L;
    public UserInfo user_info;

    public class UserInfo extends BaseBean{

        private static final long serialVersionUID = -7801026131792080237L;
        public int uid;
        public String username;
        public String nickname;
        public int sex;
        public String birthday;
        public int avatar;
        public int cover_id;
        public int status;
        public int type;
        public String occupation;
        public String industry;
        public String signature;
        public String user_token;
        public String sex_format;
        public String avatar_url;
        public String cover_url;
        public String mobile;

    }
}
