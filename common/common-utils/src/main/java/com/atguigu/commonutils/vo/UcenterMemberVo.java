package com.atguigu.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by corey on 2021/7/31
 **/

@Data
public class UcenterMemberVo {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户手机号")
    private String mobile;
}
