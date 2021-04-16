package com.gxd.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:41:41
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

