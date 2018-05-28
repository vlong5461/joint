package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ExpertDetailBean extends BaseBean {
    private static final long serialVersionUID = -2218358056821352077L;
    public Expertdetail expert_detail;

    public class Expertdetail extends BaseBean{
        private static final long serialVersionUID = -1830317952391662646L;
        public int uid;
        public int cover_id;
        public int attention_num;
        public String username;
        public String signature;
        public String cover_url;
    }
}
