package com.gxd.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:41:40
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

