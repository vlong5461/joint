package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MaterialCategoryBean extends BaseBean {
    private static final long serialVersionUID = 4232076297261884730L;
    public List<CategoryList> category_list;

    public class CategoryList extends BaseBean{

        private static final long serialVersionUID = -7585497185832623187L;
        public int id;
        public String title;
        public int icon;
        public String icon_url;
    }
}
