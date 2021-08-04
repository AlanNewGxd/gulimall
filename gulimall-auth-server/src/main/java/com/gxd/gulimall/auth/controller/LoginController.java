package com.gxd.gulimall.auth.controller;

import com.gxd.common.constant.AuthServerConstant;
import com.gxd.common.exception.BizCodeEnum;
import com.gxd.common.utils.R;
import com.gxd.gulimall.auth.feign.ThirdPartFeignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author guxiaodong
 * @version 1.0
 * @title 登录控制类
 * @date 2021/8/2 14:56
 */
@Controller
public class LoginController {
//    @GetMapping("login.html")
//    public String loginPage(){
//        return "login";
//    }
//
//    @GetMapping("reg.html")
//    public String regPage(){
//        return "reg";
//    }
    @Resource
    private ThirdPartFeignService thirdPartFeignService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping(value = "/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {

        //1、接口防刷
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            //活动存入redis的时间，用当前时间减去存入redis的时间，判断用户手机号是否在60s内发送验证码
            long currentTime = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - currentTime < 60000) {
                //60s内不能再发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(),BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        //2、验证码的再次效验 redis.存key-phone,value-code
//        String code = UUID.randomUUID().toString().substring(0, 5);
//        String redisValue = code+"_"+System.currentTimeMillis();
        // 验证码只可以是数字
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        String codeNum = String.valueOf(code);
        String redisStorage = codeNum + "_" + System.currentTimeMillis();

        //存入redis，防止同一个手机号在60秒内再次发送验证码
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone,
                redisStorage,10, TimeUnit.MINUTES);

        thirdPartFeignService.sendCode(phone, codeNum);

        return R.ok();
    }

}
