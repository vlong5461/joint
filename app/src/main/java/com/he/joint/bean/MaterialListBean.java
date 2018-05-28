package com.he.joint.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MaterialListBean extends BaseBean {
    private static final long serialVersionUID = -4724355162105427828L;
    public MaterialList material_list;

    public class MaterialList extends BaseBean{

        private static final long serialVersionUID = -158702464003445443L;
        public int total;
        public int per_page;
        public int current_page;
        public int last_page;
        public List<Data> data;
    }
    public class Data extends BaseBean{

        private static final long serialVersionUID = 1714041230369173520L;
        public int id;
        public String title;
        public String description;
        public int create_time;
        public int view;
        public String create_time_format;
    }
}
