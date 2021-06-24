package com.gxd.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.ware.entity.WareSkuEntity;
import com.gxd.gulimall.ware.vo.SkuHasStockVo;
import com.gxd.gulimall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:48:21
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    boolean orderLockStock(WareSkuLockVo vo);
}

