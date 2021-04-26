package com.sysu.guli.service.edu.controller.api;

import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Subject;
import com.sysu.guli.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "课程分类")
@RestController
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList() {
        List<Subject> subjectVoList = subjectService.getNestedList();
        return R.ok().data("items", subjectVoList);
    }


}