package com.gxd.gulimall.thirdparty.controller;

import com.gxd.common.utils.R;
import com.gxd.gulimall.thirdparty.component.DxSmsComponent;
import com.gxd.gulimall.thirdparty.component.SmsComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
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
    @GetMapping(value = "/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {
        //发送验证码
        dxSmsComponent.sendCode(phone);
        return R.ok();
    }
}
