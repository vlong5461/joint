package com.he.joint.bean;

import java.util.List;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class NewsFlashListBean extends BaseBean {
    private static final long serialVersionUID = -4669454791959820611L;

    public NewsFlashList news_flash_list;

    public class NewsFlashList extends BaseBean{
        private static final long serialVersionUID = 7037621369137872458L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Data> data;
    }

    public class Data extends BaseBean{

        private static final long serialVersionUID = -8461827812810293724L;
        public int id;
        public String title;
        public int uid;
        public int create_time;
        public String publisher;
        public String create_time_format;

    }
}
