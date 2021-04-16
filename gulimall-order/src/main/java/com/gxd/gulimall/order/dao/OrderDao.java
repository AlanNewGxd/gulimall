package com.gxd.gulimall.order.dao;

import com.gxd.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:41:41
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
