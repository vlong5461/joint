package com.he.joint.bean;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ReportDetailBean extends BaseBean {

    private static final long serialVersionUID = -8973684876692942447L;
    public Reportdetail report_detail;

    public class Reportdetail extends BaseBean{
        private static final long serialVersionUID = -5725875485220711442L;
        public int id;
        public String attach_url;
    }
}
