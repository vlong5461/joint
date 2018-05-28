package com.he.joint.bean;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class NewsDetailBean extends BaseBean {
    private static final long serialVersionUID = 7013729357722814485L;
    public DetailInfo detail_info;

    public class DetailInfo extends BaseBean{
        private static final long serialVersionUID = -4853719753053825356L;
        public int id;
        public String title;
        public int uid;
        public int create_time;
        public String description;
        public String content;
        public int view;
        public String publisher;
        public int favorite_counts;
    }
}
