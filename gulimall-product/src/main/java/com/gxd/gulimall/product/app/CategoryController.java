package com.gxd.gulimall.product.app;

import com.gxd.common.utils.R;
import com.gxd.gulimall.product.entity.CategoryEntity;
import com.gxd.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 商品三级分类
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 *
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品3及分类所有数据，包括父级和子级
     */
    @RequestMapping("/list/tree")
    public R list(){

        List<CategoryEntity> categoryTree = categoryService.listCategoryTree();

        return R.ok().put("data", categoryTree);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
//    @RequestMapping("/update")
//    public R update(@RequestBody CategoryEntity category){
//		categoryService.updateById(category);
//
//        return R.ok();
//    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 批量修改层级
        [{"catId":1,"sort":0},{"catId":2,"catLevel":2}]
     */
    @RequestMapping("/update/sort")
    public R updateSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));
        return R.ok();
    }


    /**
     * 删除
     * @RequestBody获取请求体，必须发送post请求  springmvc 自动将请求体的数据json，转化为对应的对象
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
//		categoryService.removeByIds(Arrays.asList(catIds));   物理删除

        categoryService.removeMenuByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
