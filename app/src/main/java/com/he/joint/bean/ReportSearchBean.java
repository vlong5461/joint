package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ReportSearchBean extends BaseBean {
    private static final long serialVersionUID = 2829399579273489664L;
    public ReportListBean.ReportList report_list;

    public class ReportList extends BaseBean{
        private static final long serialVersionUID = 5797534220879091970L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<ReportListBean.Data> data;
    }

    public class Data extends BaseBean{

        private static final long serialVersionUID = -3127662809841107372L;
        public int id;
        public String title;
        public String company_name;
        public String product_type;
        public String certificate_number;

    }
}
