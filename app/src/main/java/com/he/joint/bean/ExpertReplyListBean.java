package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ExpertReplyListBean extends BaseBean {
    private static final long serialVersionUID = 6990530783741338799L;
    public ReplyList reply_list;
    public int reply_count;

    public class ReplyList extends BaseBean{

        private static final long serialVersionUID = 2228231009346527106L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;
    }
    public class Datum extends BaseBean{

        private static final long serialVersionUID = -6571239880545370519L;
        public int id;
        public int uid;
        public String title;
        public String content;
        public int create_at;
        public String pic_id;
        public String answer_content;
        public int audio_id;
        public String create_at_format;
        public String pic_url;
        public String question_avatar_url;
        public String question_username;
        public String answer_avatar_url;
        public String answer_audio_url;
    }
}
