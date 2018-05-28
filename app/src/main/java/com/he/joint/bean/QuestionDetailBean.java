package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class QuestionDetailBean extends BaseBean {
    private static final long serialVersionUID = -1303539905996680787L;
    public QuestionDetail question_detail;

    public class QuestionDetail extends BaseBean{

        private static final long serialVersionUID = -6448948854414034105L;
        public int id;
        public String title;
        public String content;
        public int uid;
        public int create_at;
        public String pic_id;
        public int likes;
        public int reads;
        public String create_at_format;
        public String question_avatar_url;
        public String question_username;
        public List<String> pic_url;
        public String answer_content;
        public String answer_username;
        public String answer_avatar_url;
        public String answer_audio_url;
    }
}
