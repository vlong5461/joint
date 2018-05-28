package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MessageListsBean extends BaseBean {
    private static final long serialVersionUID = 2924914928287993563L;
    public MessageList message_list;

    public class MessageList extends BaseBean{

        private static final long serialVersionUID = 760489367987238539L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;
    }

    public class Datum extends BaseBean{

        private static final long serialVersionUID = 2739145365408202323L;
        public int id;
        public String title;
        public String content;
        public int send_at;
        public String send_at_format;
    }
}
