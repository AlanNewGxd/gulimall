package com.gxd.gulimall.product.app;

import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.R;
import com.gxd.gulimall.product.entity.AttrEntity;
import com.gxd.gulimall.product.entity.AttrGroupEntity;
import com.gxd.gulimall.product.service.AttrGroupService;
import com.gxd.gulimall.product.service.AttrService;
import com.gxd.gulimall.product.service.CategoryService;
import com.gxd.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表（多参数获取）
     * @param  catelogId 0的话查所有
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,@PathVariable Long catelogId){
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }

//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{attrGroupId}")
//    public R info(@PathVariable("attrGroupId") Long attrGroupId){
//		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
//
//        return R.ok().put("attrGroup", attrGroup);
//    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        // 用当前当前分类id查询完整路径并写入 attrGroup
        Long[] catelogIds= categoryService.findCateLogPath(attrGroup.getCatelogId());
        attrGroup.setCatelogIds(catelogIds);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * 获取属性分组有关联的其他属性
     * @param attrgroupId
     * @return
     */
    ///product/attrgroup/{attrgroupId}/attr/relation
    @GetMapping(value = "/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {

        List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);

        return R.ok().put("data",entities);
    }

    /**
     * 获取属性分组没有关联的其他属性（新建关联）
     */
    @GetMapping(value = "/{attrgroupId}/noattr/relation")
    public R attrNoattrRelation(@RequestParam Map<String, Object> params,
                                @PathVariable("attrgroupId") Long attrgroupId) {

        // List<AttrEntity> entities = attrService.getRelationAttr(attrgroupId);

        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page",page);
    }

    // /product/attrgroup/{catelogId}/withattr
    // 列出当前属性分组 关联的 所有属性
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        // 1、查出当前分类下的所有分组

        // 2、查出每个分组关联的所有基本属性
        List<AttrGroupWithAttrsVo> data = attrGroupService.getAttrGroupWithAttrs(catelogId);

        return R.ok().put("data", data);
    }

}
