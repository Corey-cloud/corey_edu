package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by corey on 2021/7/20
 **/

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody   //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error();
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        //log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
