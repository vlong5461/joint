package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UserEditBean extends BaseBean {
    private static final long serialVersionUID = -5415824751923744454L;
    public UserInfo user_info;

    public class UserInfo extends BaseBean{
        private static final long serialVersionUID = 8473336590783772788L;
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
    }
}
