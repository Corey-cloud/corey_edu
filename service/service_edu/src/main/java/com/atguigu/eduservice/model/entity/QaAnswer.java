package com.atguigu.eduservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="QaAnswer对象", description="问题--回帖详情表")
public class QaAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键自增id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "会员id",required = true)
    private String memberId;

    @ApiModelProperty(value = "会员名称")
    private String memberNickname;

    @ApiModelProperty(value = "会员头像")
    private String memberAvatar;

    @ApiModelProperty(value = "问题帖子id",required = true)
    private String questionId;

    @ApiModelProperty(value = "回帖详情",required = true)
    private String answerDetails;

    @ApiModelProperty(value = "2级回复",required = false)
    @TableField(exist = false)
    private List<QaSecondAnswer> answer2List;

    @ApiModelProperty(value = "点赞数")
    private Integer zanCount;

    @ApiModelProperty(value = "ip归属地")
    private String comeFrom;

    @ApiModelProperty(value = "删除标识")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "备注")
    private String remark;

}
