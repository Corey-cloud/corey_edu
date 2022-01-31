package com.atguigu.eduservice.controller.admin;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.vo.VideoInfoVo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation(value = "新增课时")
    @PostMapping("save-video-info")
    public R save(
            @ApiParam(name = "videoForm", value = "课时对象", required = true)
            @RequestBody VideoInfoVo videoInfoForm){
        videoService.saveVideoInfo(videoInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课时")
    @GetMapping("video-info/{id}")
    public R getVideInfoById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        VideoInfoVo videoInfoForm = videoService.getVideoInfoFormById(id);
        return R.ok().data("item", videoInfoForm);
    }

    @ApiOperation(value = "更新课时")
    @PutMapping("update-video-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
            @RequestBody VideoInfoVo videoInfoForm,
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        videoInfoForm.setId(id);
        videoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据ID删除课时")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课时ID", required = true)
            @PathVariable String id){
        boolean result = videoService.removeVideoById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

