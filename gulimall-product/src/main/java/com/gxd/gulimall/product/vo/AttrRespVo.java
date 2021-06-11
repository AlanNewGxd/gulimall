package com.gxd.gulimall.product.vo;

import lombok.Data;

/**
 * @Description AttrRespVo
 * @Param
 * @Author guxiaodong
 * @Date 9:47 2021/6/11
 **/

@Data
public class AttrRespVo extends AttrVo {

    /**
     *
     */
    private String catelogName;

    private String groupName;

    //catelogPath == catelogIds
    private Long[] catelogIds;

}

