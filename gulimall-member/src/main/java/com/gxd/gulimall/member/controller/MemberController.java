package com.gxd.gulimall.member.controller;

import com.gxd.common.exception.BizCodeEnum;
import com.gxd.common.utils.PageUtils;
import com.gxd.common.utils.R;
import com.gxd.gulimall.member.entity.MemberEntity;
import com.gxd.gulimall.member.exception.PhoneException;
import com.gxd.gulimall.member.exception.UsernameException;
import com.gxd.gulimall.member.feign.CouponFeignService;
import com.gxd.gulimall.member.service.MemberService;
import com.gxd.gulimall.member.vo.MemberUserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author alannew
 * @email 86466280@qq.com
 * @date 2021-04-16 09:32:38
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    /*
     * @Description 获取会员优惠券信息
     * @Param []
     * @Author guxiaodong
     * @Date 10:54 2021/4/22
     **/
    @RequestMapping("/couponsList")
    public R getMemberCouponList(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("尊贵的张三");
        R membercoupons = couponFeignService.membercoupons();
        R.ok().put("member",memberEntity);
        R.ok().put("couponsList",membercoupons.get("coupons"));
        System.out.println(R.ok());
        return R.ok().put("member",memberEntity).put("couponsList",membercoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @PostMapping(value = "/register")
    public R register(@RequestBody MemberUserRegisterVo vo) {
        try {
            memberService.register(vo);
        } catch (PhoneException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(),BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

}
