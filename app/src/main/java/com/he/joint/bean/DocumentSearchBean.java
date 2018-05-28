package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class DocumentSearchBean extends BaseBean {
    private static final long serialVersionUID = -5617247850594578005L;
    private ResultList result_list;

    public class ResultList extends BaseBean{
        private static final long serialVersionUID = -5655740556450634511L;
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<Datum> data;
    }
    public class Datum extends BaseBean{
        private int id;
        private String title;
        private String company_name;
        private String product_type;
        private String certificate_number;
    }
}
