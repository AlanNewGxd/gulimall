package com.gxd.gulimall.product.dao;

import com.gxd.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
