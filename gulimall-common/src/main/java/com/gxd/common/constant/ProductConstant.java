package com.gxd.common.constant;

/**
 * @Description 商品常量属性
 * @Param 
 * @Author guxiaodong
 * @Date 17:18 2021/6/10
 **/
public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_BASE(1,"基本属性"),
        ATTR_TYPE_SALE(0,"销售属性");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }


}
