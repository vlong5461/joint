package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class QuestionListsBean extends BaseBean {

    private static final long serialVersionUID = -5742199314276311069L;
    public QuestionList question_list;

    public class QuestionList extends BaseBean{

        private static final long serialVersionUID = 8468927531346203126L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;
    }

    public class Datum extends BaseBean{

        private static final long serialVersionUID = -1835261275195513936L;
        public int id;
        public String title;
        public String content;
        public int uid;
        public int create_at;
        public String pic_id;
        public int likes;
        public int reads;
        public String question_avatar_url;
        public String question_username;
        public String pic_url;
        public String answer_content;
        public String answer_avatar_url;
        public String answer_username;
        public String answer_audio_url;
        public String create_at_format;

    }
}
