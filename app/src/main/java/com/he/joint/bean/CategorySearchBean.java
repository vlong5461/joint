package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class CategorySearchBean extends BaseBean {
    private static final long serialVersionUID = 2009276385263820407L;
    public List<CategoryList> category_list;

    public class CategoryList extends BaseBean{
        private static final long serialVersionUID = -187396176233388318L;
        public int id;
        public String title;
        public int count;
    }
}
