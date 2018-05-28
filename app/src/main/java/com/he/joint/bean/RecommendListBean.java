package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class RecommendListBean extends BaseBean {
    private static final long serialVersionUID = 6780446503194285188L;
    public RecommendList recommend_list;

    public class RecommendList extends BaseBean{
        private static final long serialVersionUID = 3294883501443029938L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Data> data;
    }

    public class Data extends BaseBean{
        private static final long serialVersionUID = 6213226718953078763L;
        public int id;
        public String title;
        public int cover_id;
        public int uid;
        public int create_time;
        public String publisher;
        public String cover_url;
        public int favorite_counts;
    }
}
