package com.atguigu.eduservice.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.entity.EduVideo;
import com.atguigu.eduservice.model.vo.VideoInfoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Api(description="课时管理")
//@CrossOrigin //跨域
@RestController
@RequestMapping("/edu/admin/videos")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "根据ID查询视频")
    @GetMapping("/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "视频ID", required = true)
            @PathVariable String id){
        VideoInfoVo videoInfoForm = videoService.getVideoInfoFormById(id);
        return R.ok().data("item", videoInfoForm);
    }

    @ApiOperation(value = "新增视频")
    @PostMapping
    public R save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoForm){
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean save = videoService.save(video);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "更新课时")
    @PutMapping
    public R updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoVo videoInfoForm){
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean flag = videoService.updateById(video);
        return flag ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID删除视频")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        boolean result = videoService.removeVideoById(id);
        return result ? R.ok() : R.error();
    }
}

