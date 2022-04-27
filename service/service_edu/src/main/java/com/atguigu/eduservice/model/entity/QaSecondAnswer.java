package com.atguigu.eduservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by corey on 2022/3/31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="QaSecondAnswer对象", description="")
public class QaSecondAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "回复id")
    private String answerId;

    @ApiModelProperty(value = "问题id")
    private String questionId;

    @ApiModelProperty(value = "二级回复详情")
    private String asDetails;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员昵称")
    private String memberNickname;

    @ApiModelProperty(value = "会员头像")
    private String memberAvatar;

    @ApiModelProperty(value = "删除标识",required = false)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间",required = false)
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间",required = false)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "备注",required = false)
    private String remark;

}
