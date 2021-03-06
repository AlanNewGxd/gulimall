package com.gxd.gulimall.ware.dao;

import com.gxd.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * εεεΊε­
 * 
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:48:21
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(Long skuId);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}
