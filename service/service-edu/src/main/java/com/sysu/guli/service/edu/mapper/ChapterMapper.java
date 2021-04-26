package com.sysu.guli.service.edu.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sysu.guli.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ChapterMapper extends BaseMapper<Chapter> {

    List<Chapter> nestedListByCourseId(@Param("ew") QueryWrapper<Chapter> queryWrapper);
}
