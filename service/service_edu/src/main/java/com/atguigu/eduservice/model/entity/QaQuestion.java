package com.atguigu.eduservice.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "QaQuestion对象", description = "问答-帖子表")
public class QaQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键自增id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "会员id")
    private String memberId;

    @ApiModelProperty(value = "会员名称")
    private String memberNickname;

    @ApiModelProperty(value = "会员头像")
    private String memberAvatar;

    @ApiModelProperty(value = "问题标题")
    private String questionTitle;

    @ApiModelProperty(value = "问题内容")
    private String questionDetails;

    @ApiModelProperty(value = "问题类型")
    private String qtIds;

    @ApiModelProperty(value = "回答数")
    private Integer qaComments;

    @ApiModelProperty(value = "浏览数")
    private Integer qaView;

    @ApiModelProperty(value = "帖子是否发布成功(1-成功 0-失败)")
    private Integer status;

    @ApiModelProperty(value = "发布状态")
    private Integer enable;

    @ApiModelProperty(value = "回复信息",required = false)
    @TableField(exist = false)
    private List<QaAnswer> qaAnswerList = new ArrayList<>();

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
