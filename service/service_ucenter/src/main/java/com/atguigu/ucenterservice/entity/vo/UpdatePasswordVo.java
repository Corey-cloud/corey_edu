package com.atguigu.ucenterservice.entity.vo;

import lombok.Data;

/**
 * Created by corey on 2022/4/15
 **/

@Data
public class UpdatePasswordVo {
    private String id;
    private String pass;
    private String checkPass;
}
