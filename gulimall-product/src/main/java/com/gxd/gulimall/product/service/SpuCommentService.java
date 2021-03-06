package com.gxd.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

