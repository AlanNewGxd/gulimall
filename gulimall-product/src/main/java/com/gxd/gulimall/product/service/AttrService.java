package com.gxd.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.product.entity.AttrEntity;
import com.gxd.gulimall.product.vo.AttrRespVo;
import com.gxd.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void saveAttr(AttrVo attr);

    void updateAttrById(AttrVo attr);

    /**
     * @Description 在指定的所有属性集合中，筛出检索属性集合
     * @Param [attrIds]
     * @Author guxiaodong
     * @Date 16:43 2021/6/25
     **/
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

