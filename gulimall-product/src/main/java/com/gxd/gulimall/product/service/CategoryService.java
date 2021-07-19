package com.gxd.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gxd.common.utils.PageUtils;
import com.gxd.gulimall.product.entity.CategoryEntity;
import com.gxd.gulimall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listCategoryTree();

    void removeMenuByIds(List<Long> asList);

    Long[] findCateLogPath(Long catelogId);

    void updateCascade(CategoryEntity category);

    Long[] findCatelogPath(Long catelogId);

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catalog2Vo>> getCatalogJson();

    Map<String, List<Catalog2Vo>> getCatalogJson2();
}

