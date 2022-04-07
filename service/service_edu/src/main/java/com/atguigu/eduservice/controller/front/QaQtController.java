package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.model.frontvo.QaTypeVo;
import com.atguigu.eduservice.service.QaQtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by corey on 2022/3/31
 **/

@RestController
@RequestMapping("/edu/qa-qt")
@Api(tags = {"问答类型-前台"})
public class QaQtController {

    @Autowired
    private QaQtService qtService;

    @GetMapping("/getTypes")
    @ApiOperation(value = "查询所有类型，供前台用户选择")
    public R getTypes(){
        List<QaTypeVo> qTypeVoList= qtService.queryTypes();
        return R.ok().data("qTypeVoList", qTypeVoList);
    }


}
