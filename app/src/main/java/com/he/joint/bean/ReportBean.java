package com.he.joint.bean;

import java.util.List;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class ReportBean extends BaseBean {
    private static final long serialVersionUID = 7645682421539980455L;
    public List<Category> category;
    public List<SubCategory> sub_category;

    public class Category extends BaseBean{

        private static final long serialVersionUID = -7478439468725388828L;
        public int id;
        public String title;
        public int icon;
        public List<Child> child;
        public String icon_url;

    }
    public class SubCategory extends BaseBean{

        private static final long serialVersionUID = 350796734628522594L;
        public int id;
        public String title;
    }

    public class Child extends BaseBean{

        private static final long serialVersionUID = -3940941205176162313L;
        public int id;
        public String title;
        public int icon;
        public List<String> child;
    }
}
