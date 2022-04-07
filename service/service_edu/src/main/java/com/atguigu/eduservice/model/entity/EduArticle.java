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
@ApiModel(value="EduArticle对象", description="文章详情表")
public class EduArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增主键ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "文章内容")
    private String contentDetails;

    @ApiModelProperty(value = "文章标题")
    private String contentTitle;

    @ApiModelProperty(value = "文章缩略图")
    private String contentImg;

    @ApiModelProperty(value = "文章描述")
    private String contentDescription;

    @ApiModelProperty(value = "文章作者")
    private String contentAuthor;

    @ApiModelProperty(value = "文章来源")
    private String contentSource;

    @ApiModelProperty(value = "文章类型")
    private String contentType;

    @ApiModelProperty(value = "点赞数")
    private Integer contentHit;

    @ApiModelProperty(value = "评论数")
    private Integer contentComment;

    @ApiModelProperty(value = "浏览数")
    private Integer contentView;

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
