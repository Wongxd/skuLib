package io.github.wongxd.skulibray.specSelect.bean;

import java.util.List;

/**
 * Created by wongxd on 2018/1/29.
 * <p>
 * 规格信息的pojo
 * <p>
 * 你的 CombsBean 数据可以多余当前给出的，但不能没有这个类中定义的数据.
 * <p>
 * 注意，ValueBean {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean.AttrsBean.ValueBean} 是不可以被拓展的。因为在
 * AttrsBean {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean.AttrsBean} 中 value 是个 List ，
 * 经过查看sun的说明文档，在java中是”不能创建一个确切的泛型类型的数组”的。
 * 所以，把 AttrsBean {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean.AttrsBean} 设置为 final。
 * <p>
 * CombsBean  {@link io.github.wongxd.skulibray.specSelect.bean.SpecBean.CombsBean}  同理
 * <p>
 * 可能你们后台返回的 AttrsBean.ValueBean 中，还会有 对应于 key 的 唯一的 id 值，以供属性分组使用，但是你可以选择不使用，因为，
 * 在代码中，我已经为每个属性组生成了 groupId。
 */

public class SpecBean {


//          {"data" : {
//       "attrs":[
//                {"value":[{"id":3,"name":"红色"}], "key" : "颜色"}
//               ],
//
//      "combs":[
//                {"id":10,"productId":5,"stock":2,"comb":"4,6,23,20","desc":"蓝色-20KG-绵阳-30cm","price":1.0,specImg:""}
//              ]
//
//      }}


    private List<AttrsBean> attrs;
    private List<CombsBean> combs;

    public List<AttrsBean> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttrsBean> attrs) {
        this.attrs = attrs;
    }

    public List<CombsBean> getCombs() {
        return combs;
    }

    public void setCombs(List<CombsBean> combs) {
        this.combs = combs;
    }

    public static final class AttrsBean {
        /**
         * value : [{"id":3,"name":"红色"}]
         * key : 颜色
         */

        private String key;
        private List<ValueBean> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static final class ValueBean {
            /**
             * id : 3   对应 combs->comb 中的数字
             * name : 红色
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static final class CombsBean {
        /**
         * id : 10
         * productId : 5
         * stock : 2
         * comb : 4,6,23,20
         * desc : 蓝色-20KG-绵阳-30cm
         * price : 1.0
         * specImg:""
         */

        private long id = 0L; // 有的 后台 使用 id 去判断选择的规格
        private long productId;
        private String productName;
        private int stock;
        private String comb;  // 有的 后台 使用 组合 去判断选择的规格
        private String desc;
        private String specImg = ""; // 当前规格 对应的图片(非必需)
        private double price;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getComb() {
            return comb;
        }

        public void setComb(String comb) {
            this.comb = comb;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSpecImg() {
            return specImg;
        }

        public void setSpecImg(String specImg) {
            this.specImg = specImg;
        }
    }


}
