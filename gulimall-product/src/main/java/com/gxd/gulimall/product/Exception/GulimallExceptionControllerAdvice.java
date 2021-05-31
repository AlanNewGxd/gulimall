package com.gxd.gulimall.product.Exception;

import com.gxd.common.exception.BizCodeEnum;
import com.gxd.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guxiaodong
 * @version 1.0
 * @title 异常统一处理类
 * @date 2021/5/31 16:15
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.gxd.gulimall.product.controller")//管理的controller
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = Exception.class) // 也可以返回ModelAndView
    public R handleValidException(MethodArgumentNotValidException exception){

        Map<String,String> map=new HashMap<>();
        // 获取数据校验的错误结果
        BindingResult bindingResult = exception.getBindingResult();
        // 处理错误
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String message = fieldError.getDefaultMessage();
            String field = fieldError.getField();
            map.put(field,message);
        });

        log.error("数据校验出现问题{},异常类型{}",exception.getMessage(),exception.getClass());

        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),
                BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data",map);
    }

    @ExceptionHandler(value = Throwable.class)//异常的范围更大
    public R handleException(Throwable throwable){
        log.error("未知异常{},异常类型{}",
                throwable.getMessage(),
                throwable.getClass());
        return R.error(BizCodeEnum.UNKNOW_EXEPTION.getCode(),
                BizCodeEnum.UNKNOW_EXEPTION.getMsg());
    }

}

