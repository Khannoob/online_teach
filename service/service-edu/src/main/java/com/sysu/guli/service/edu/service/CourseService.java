package com.sysu.guli.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sysu.guli.service.edu.entity.form.CourseInfoForm;
import com.sysu.guli.service.edu.entity.query.QueryCourseBean;
import com.sysu.guli.service.edu.entity.query.WebCourseQuery;
import com.sysu.guli.service.edu.entity.vo.CourseVo;
import com.sysu.guli.service.edu.entity.vo.WebCourseVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    Page<CourseVo> selectPage(Integer pageNum, Integer pageSize, QueryCourseBean queryCourseBean);

    CourseInfoForm getCourseById(String id);

    void updateCourseInfoById(CourseInfoForm form);

    CourseVo getCoursePublishVoById(String id);

    List<Course> getCoursesByTid(String tid);

    List<Course> getCourses(WebCourseQuery webCourseQuery);

    WebCourseVo getCourseDetail(String id);
}
