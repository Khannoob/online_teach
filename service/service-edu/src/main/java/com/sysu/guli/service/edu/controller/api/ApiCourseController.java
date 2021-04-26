package com.sysu.guli.service.edu.controller.api;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Course;
import com.sysu.guli.service.edu.entity.form.CourseInfoForm;
import com.sysu.guli.service.edu.entity.query.QueryCourseBean;
import com.sysu.guli.service.edu.entity.query.WebCourseQuery;
import com.sysu.guli.service.edu.entity.vo.CourseVo;
import com.sysu.guli.service.edu.entity.vo.WebCourseVo;
import com.sysu.guli.service.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/api/edu/course")

public class ApiCourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("course-detail/{id}")
    public R getCourseDetail(@PathVariable String id){
        Course course = courseService.getById(id);
        course.setViewCount((course.getViewCount()+1));
        courseService.updateById(course);
        WebCourseVo webCourseVos = courseService.getCourseDetail(id);
        return R.ok().data("item", webCourseVos);
    }

    @GetMapping("get-courses-by-tid/{tid}")
    public R getCoursesByTid(@PathVariable String tid) {

        List<Course> courses = courseService.getCoursesByTid(tid);
        return R.ok().data("items", courses);
    }

    @GetMapping("list")
    public R getCourses(WebCourseQuery webCourseQuery){
        List<Course> courses = courseService.getCourses(webCourseQuery);
        return R.ok().data("items", courses);
    }

}

