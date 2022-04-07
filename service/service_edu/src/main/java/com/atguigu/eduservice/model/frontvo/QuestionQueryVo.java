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
@ApiModel(value="QuestionQueryVo对象", description="问答查询对象")
public class QuestionQueryVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问答类型：1-最新 2-最热 3-等待回答",required = false)
    private String qt;

    @ApiModelProperty(value = "问答状态-用于管理员是否审批",required = false)
    private Integer status;

    @ApiModelProperty(value = "问答状态-是否发布成功",required = false)
    private Integer enable;

}
