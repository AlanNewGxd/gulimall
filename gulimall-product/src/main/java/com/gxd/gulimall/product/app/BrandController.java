package com.gxd.gulimall.product.app;

import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.R;
import com.gxd.common.valid.UpdateGroup;
import com.gxd.gulimall.product.entity.BrandEntity;
import com.gxd.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



/**
 * 品牌
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-13 17:18:31
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存  @Valid这个方法需要校验
     */
    @RequestMapping("/save")
    public R save(@Valid @RequestBody BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    @RequestMapping("/save2")
    public R save2(@Valid @RequestBody BrandEntity brand, BindingResult result){
        // 手动处理异常
        if( result.hasErrors()){
            Map<String,String> map=new HashMap<>();
            //1.获取错误的校验结果
            result.getFieldErrors().forEach((item)->{
                //获取发生错误时的message
                String message = item.getDefaultMessage();
                //获取发生错误的字段
                String field = item.getField();
                map.put(field,message);
            });
            return R.error(400,"提交的数据不合法").put("data",map);
        }else {

        }
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateDetails(brand);

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
