package com.sysu.guli.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.bind.v2.model.core.ID;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Course;
import com.sysu.guli.service.edu.entity.form.CourseInfoForm;
import com.sysu.guli.service.edu.entity.query.QueryCourseBean;
import com.sysu.guli.service.edu.entity.vo.CourseVo;
import com.sysu.guli.service.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@RestController
@RequestMapping("/admin/edu/course")

public class AdminCourseController {
    @Autowired
    CourseService courseService;
    @PutMapping("publish/{id}")
    public R publishCourse(@PathVariable String id){

        LambdaUpdateWrapper<Course> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Course::getPublishTime,new Date());
        updateWrapper.set(Course::getStatus,"Normal");
        updateWrapper.eq(Course::getId, id);

        courseService.update(updateWrapper);
        return R.ok().setMessage("课程发布成功!");
    }
    @GetMapping("course-publish/{id}")
    public R getCoursePublishVoById(@PathVariable String id){
        CourseVo courseVo = courseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseVo);
    }


    @PutMapping("update-course-info")
    public R updateCourseInfoById(@RequestBody CourseInfoForm form){
        courseService.updateCourseInfoById(form);
        return R.ok().setMessage("修改已成功");
    }

    @GetMapping("course-info/{id}")
    public R getCourseById(@PathVariable String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @PostMapping("save-course-info")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("id", courseId);
    }

    @GetMapping("list/{pageNum}/{pageSize}")
    public R getPageList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, QueryCourseBean searchObj) {

        Page<CourseVo> page = courseService.selectPage(pageNum, pageSize, searchObj);

        return R.ok().data("page", page);
    }
}

