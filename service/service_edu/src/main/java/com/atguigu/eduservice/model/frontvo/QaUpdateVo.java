package com.atguigu.eduservice.model.frontvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by corey on 2022/4/4
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="QTypeVo对象", description="问答类型")
public class QaUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id",required = true)
    private Integer id;

    @ApiModelProperty(value = "问答状态-是否发布成功",required = false)
    private Integer enable;

    @ApiModelProperty(value = "备注")
    private String remark;

}
