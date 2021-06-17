package com.gxd.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.to.SkuReductionTo;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-14 17:26:29
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

