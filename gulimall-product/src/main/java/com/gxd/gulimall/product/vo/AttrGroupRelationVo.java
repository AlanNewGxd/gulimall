package com.gxd.gulimall.product.vo;

import lombok.Data;

/**
 * @Description AttrGroupRelationVo
 * @Param 
 * @Author guxiaodong
 * @Date 14:44 2021/6/11
 **/

@Data
public class AttrGroupRelationVo {

    //[{"attrId":1,"attrGroupId":2}]
    private Long attrId;

    private Long attrGroupId;

}
