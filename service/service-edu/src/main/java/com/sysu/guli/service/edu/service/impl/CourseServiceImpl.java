package com.sysu.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.edu.entity.Course;
import com.sysu.guli.service.edu.entity.CourseDescription;
import com.sysu.guli.service.edu.entity.Teacher;
import com.sysu.guli.service.edu.entity.form.CourseInfoForm;
import com.sysu.guli.service.edu.entity.query.QueryCourseBean;
import com.sysu.guli.service.edu.entity.query.WebCourseQuery;
import com.sysu.guli.service.edu.entity.vo.CourseVo;
import com.sysu.guli.service.edu.entity.vo.WebCourseVo;
import com.sysu.guli.service.edu.mapper.*;
import com.sysu.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseDescriptionMapper courseDescriptionMapper;

    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.insert(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());//使用courseId 不自动生成id
        courseDescriptionMapper.insert(courseDescription);


        return course.getId();
    }

    @Override
    public Page<CourseVo> selectPage(Integer pageNum, Integer pageSize, QueryCourseBean queryCourseBean) {
        String title = queryCourseBean.getTitle();
        String teacherId = queryCourseBean.getTeacherId();
        String subjectId = queryCourseBean.getSubjectId();
        String subjectParentId = queryCourseBean.getSubjectParentId();
        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }
        Page<CourseVo> page = new Page<>(pageNum, pageSize);
        List<CourseVo> records = baseMapper.selectPageByQueryWrapper(page, queryWrapper);
        page.setRecords(records);
        return page;
    }

    @Override
    public CourseInfoForm getCourseById(String id) {
        CourseInfoForm form = new CourseInfoForm();
        Course course = baseMapper.selectById(id);
        if (course != null) {
            BeanUtils.copyProperties(course, form);
            CourseDescription description = courseDescriptionMapper.selectById(id);
            if (description != null) {
                form.setDescription(description.getDescription());
            }
        }
        return form;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm form) {
        Course course = new Course();
        CourseDescription courseDescription = new CourseDescription();
        if (form != null) {
            BeanUtils.copyProperties(form, course);
            baseMapper.updateById(course);
            courseDescription.setDescription(form.getDescription());
            courseDescription.setId(course.getId());//保持课程描述和课程id一致
            int i = courseDescriptionMapper.updateById(courseDescription);
            if (i == 0) {
                //如果之前没有课程描述则新增
                courseDescriptionMapper.insert(courseDescription);
            }
        }
    }



    @Override
    public CourseVo getCoursePublishVoById(String courseId) {

        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c.id", courseId);
        CourseVo courseVo = baseMapper.selectCourseVoById(queryWrapper);

        return courseVo;
    }

    @Override
    public List<Course> getCoursesByTid(String tid) {

        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Course::getTeacherId,tid);
        List<Course> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public List<Course> getCourses(WebCourseQuery webCourseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //价格排序方式 价格正序：1，价格倒序：2
        Integer type = webCourseQuery.getType();
        String buyCountSort = webCourseQuery.getBuyCountSort();
        String priceSort = webCourseQuery.getPriceSort();
        String publishTimeSort = webCourseQuery.getPublishTimeSort();
        String subjectId = webCourseQuery.getSubjectId();
        String subjectParentId = webCourseQuery.getSubjectParentId();

        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(publishTimeSort)){
            queryWrapper.orderByDesc("publish_time");
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            queryWrapper.orderByDesc("buy_count");
        }
//        queryWrapper.orderByDesc("buy_count");


        if (!StringUtils.isEmpty(priceSort)){
            if(type == null || type == 1){
                queryWrapper.orderByAsc("price");
            }else {
                queryWrapper.orderByDesc("price");
            }
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public WebCourseVo getCourseDetail(String id) {
        QueryWrapper<WebCourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.id", id);
        queryWrapper.orderByAsc("c_sort").orderByAsc("v_sort");
        WebCourseVo webCourseVos = baseMapper.getCourseDetail(queryWrapper);
        return webCourseVos;
    }

}
