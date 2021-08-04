package com.gxd.gulimall.thirdparty.controller;

import com.gxd.common.utils.R;
import com.gxd.gulimall.thirdparty.component.DxSmsComponent;
import com.gxd.gulimall.thirdparty.component.SmsComponent;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author AlanNew
 */
@RestController
@RequestMapping(value = "/sms")
public class SmsSendController {

    @Resource
    private SmsComponent smsComponent;

    @Resource
    private DxSmsComponent dxSmsComponent;

    /**
     * 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @RequestMapping(value = "/sendCode",method = RequestMethod.GET)
    public R sendCode(@RequestParam("phone") String phone,@RequestParam("code") String code) {
        //发送验证码
        dxSmsComponent.sendCode(phone,code);
        return R.ok();
    }
}
