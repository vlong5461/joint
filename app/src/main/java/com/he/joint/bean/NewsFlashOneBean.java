package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class NewsFlashOneBean extends BaseBean{
    private static final long serialVersionUID = -1554327131517550278L;
     public NewsOneBean news_flash_one;

    public class NewsOneBean extends BaseBean{

        private static final long serialVersionUID = 9125153583214593340L;
        public int id;
        public int cover_id;
        public String title;
        public String cover_url;
    }
}
