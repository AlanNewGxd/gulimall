package com.gxd.gulimall.coupon.controller;

import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.R;
import com.gxd.gulimall.coupon.entity.CouponEntity;
import com.gxd.gulimall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 优惠券信息
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-14 17:26:29
 */
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * @Description 测试获取会员下的所属优惠券信息
     * @Param   R: 全系统的所有返回类型都是
     * @Author guxiaodong
     * @Date 17:14 2021/4/21
     **/
    @RequestMapping("/member/couponsList")
    public R membercoupons(){
        //正常查库，我们这里简化，先构造一个优惠券返回
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("满200减40优惠券");
        return R.ok().put("coupons",Arrays.asList(couponEntity));
    }
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
