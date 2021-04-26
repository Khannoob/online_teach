package com.sysu.guli.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sysu.guli.service.edu.entity.query.QueryTeacherBean;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
public interface TeacherService extends IService<Teacher> {

    Page<Teacher> queryPageConditions(Page<Teacher> page, QueryTeacherBean queryTeacherBean);

    boolean removeAvatarById(String module,String id);
}
