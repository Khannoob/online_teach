package com.sysu.guli.service.edu.controller.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Teacher;
import com.sysu.guli.service.edu.entity.query.QueryTeacherBean;
import com.sysu.guli.service.edu.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Slf4j
//允许跨域
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("list")
    public R listAllTeacher() {
        List<Teacher> list = teacherService.list();
        return R.ok().data("teacherList",list).setMessage("获取讲师列表成功");
    }
    
    @GetMapping("get/{id}")
    public R getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }
}

