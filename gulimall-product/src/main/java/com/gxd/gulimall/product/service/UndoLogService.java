package com.gxd.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.product.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:30
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

