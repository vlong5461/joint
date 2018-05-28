package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ExpertListBean extends BaseBean {
    private static final long serialVersionUID = -1428367195972040449L;
    public ExpertList expert_list;

    public class ExpertList extends BaseBean{

        private static final long serialVersionUID = 6580415524179887368L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Data> data;
    }

    public class Data extends BaseBean{

        private static final long serialVersionUID = 6385315531646569342L;
        public int uid;
        public String username;
        public int cover_id;
        public String signature;
        public int answers;
        public String cover_url;

    }
}
