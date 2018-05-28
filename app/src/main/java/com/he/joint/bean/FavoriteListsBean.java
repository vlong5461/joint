package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class FavoriteListsBean extends BaseBean {
    private static final long serialVersionUID = 5055372240537601887L;
    public FavoriteList favorite_list;

    public class FavoriteList extends BaseBean{

        private static final long serialVersionUID = 1479022163504471885L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;
    }

    public class Datum extends BaseBean{
        private static final long serialVersionUID = -8479510696335052190L;
        public int article_id;
        public String title;
        public int create_at;
        public String publisher;
        public int favorite_num;
        public String cover_url;
        public String create_at_format;
    }
}
