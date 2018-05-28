package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class NoticeListBean extends BaseBean {
    private static final long serialVersionUID = 8920634068568026317L;
    public NoticeList notice_list;

    public class NoticeList extends BaseBean{

        private static final long serialVersionUID = 9095882118886731777L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;

    }
    public class Datum extends BaseBean{

        private static final long serialVersionUID = 2454326121986742955L;
        public int id;
        public String title;
        public String content;
        public int send_at;
        public String send_at_format;
    }
}
