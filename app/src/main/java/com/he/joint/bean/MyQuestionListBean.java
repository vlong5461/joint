package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class MyQuestionListBean extends BaseBean {
    private static final long serialVersionUID = 7760347236735786162L;
    public QuestionList question_list;

    public class QuestionList extends BaseBean{

        private static final long serialVersionUID = 8286679788051045320L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Datum> data;
    }
    public class Datum extends BaseBean{

        private static final long serialVersionUID = 9208076968895987853L;
        public int id;
        public String title;
        public String content;
        public int create_at;
        public String answer_content;
        public String create_at_format;
        public String pic_url;
        public String question_avatar_url;
        public String question_username;
        public String answer_avatar_url;
        public String answer_audio_url;
    }
}
