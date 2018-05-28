package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ReportListBean extends BaseBean {
    private static final long serialVersionUID = 2511168609993022128L;
    public ReportList report_list;

    public class ReportList extends BaseBean{
        private static final long serialVersionUID = -8451195587944114780L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Data> data;
    }

    public class Data extends BaseBean{

        private static final long serialVersionUID = -5140287568123213739L;
        public int id;
        public String title;
        public String company_name;
        public String product_type;
        public String certificate_number;

    }
}
