package com.sysu.guli.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sysu.guli.service.edu.entity.vo.CourseVo;
import com.sysu.guli.service.edu.entity.vo.WebCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> selectPageByQueryWrapper(Page<CourseVo> page, @Param(Constants.WRAPPER)QueryWrapper<CourseVo> queryWrapper);


    CourseVo selectCourseVoById(@Param("ew") QueryWrapper<CourseVo> queryWrapper);

    WebCourseVo getCourseDetail(@Param("ew") QueryWrapper<WebCourseVo> queryWrapper);
}
