package com.gxd.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.order.entity.MqMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:41:41
 */
public interface MqMessageService extends IService<MqMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

