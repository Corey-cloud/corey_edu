package com.atguigu.eduservice.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by corey on 2021/7/24
 **/

@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;
    private int isFree;
}
