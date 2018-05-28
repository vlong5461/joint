package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AttentionListsBean extends BaseBean {
    private static final long serialVersionUID = -5561250099477710422L;
    public List<AttentionList> attention_list;

    public class AttentionList extends BaseBean{

        private static final long serialVersionUID = 6826254555669331694L;
        public int from_uid;
        public String cover_url;
    }
}
