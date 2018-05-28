package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AdListBean  extends BaseBean {
    private static final long serialVersionUID = -6382281327340261041L;
    public List<AdList> ad_list;

    public class AdList extends BaseBean{
        private static final long serialVersionUID = -5229729967885203154L;
        public int id;
        public String title;
        public String url;
        public String content;
        public String cover_url;
    }
}