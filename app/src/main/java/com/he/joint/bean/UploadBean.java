package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/7.
 */

public class UploadBean extends BaseBean {
    private static final long serialVersionUID = 9170691704096443464L;
    public FileInfo file_info;

    public class FileInfo extends BaseBean{
        private static final long serialVersionUID = -4643498778126180155L;
        public int id;
        public int status;
        public int create_time;
        public String path;
        public String url;
        public String md5;
        public String sha1;
    }
}
