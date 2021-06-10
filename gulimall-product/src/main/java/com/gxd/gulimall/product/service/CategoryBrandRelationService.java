package com.gxd.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetails(CategoryBrandRelationEntity categoryBrandRelation);

    void updateDetails(Long brandId, String name);
}

