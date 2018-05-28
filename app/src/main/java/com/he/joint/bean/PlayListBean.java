package com.he.joint.bean;

import java.util.List;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class PlayListBean extends BaseBean {
    private static final long serialVersionUID = -4357674052377359227L;
    public List<play_list> child;

    public class play_list extends BaseBean{

        private static final long serialVersionUID = 2707299193474821567L;
        public int id;
        public int cover_id;
        public String cover_url;
    }
}
