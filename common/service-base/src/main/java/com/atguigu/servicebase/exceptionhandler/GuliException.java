package com.atguigu.servicebase.exceptionhandler;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by corey on 2021/7/20
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {

    @ApiParam(value = "状态码")
    private Integer code;

    private String msg;
}
