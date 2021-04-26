package com.sysu.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.edu.entity.Teacher;
import com.sysu.guli.service.edu.entity.query.QueryTeacherBean;
import com.sysu.guli.service.edu.feign.OssFeignClient;
import com.sysu.guli.service.edu.mapper.TeacherMapper;
import com.sysu.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Page<Teacher> queryPageConditions(Page<Teacher> page, QueryTeacherBean queryTeacherBean) {

        String name = queryTeacherBean.getName();
        Integer level = queryTeacherBean.getLevel();
        String begin = queryTeacherBean.getJoinDateStart();
        String end = queryTeacherBean.getJoinDateEnd();

        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Teacher::getSort);

        if (queryTeacherBean == null){
            return baseMapper.selectPage(page, queryWrapper);
        }
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like(Teacher::getName,queryTeacherBean.getName());
        }
        if (level!=null){
            queryWrapper.eq(Teacher::getLevel,level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge(Teacher::getJoinDate,begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le(Teacher::getJoinDate,end);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Autowired
    OssFeignClient ossFeignClient;
    @Override
    public boolean removeAvatarById(String module,String id) {
        Teacher teacher = baseMapper.selectById(id);
        if (teacher!=null){
            String avatar = teacher.getAvatar();
            if (avatar!=null){
                System.out.println("avatar = " + avatar);
                System.out.println("module = " + module);
                ossFeignClient.deleteA(module,avatar);
                teacher.setAvatar("");
                baseMapper.updateById(teacher);
                return true;
            }
        }
        return false;
    }
}
