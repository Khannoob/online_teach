package com.sysu.guli.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.edu.entity.Teacher;
import com.sysu.guli.service.edu.service.TeacherService;
import io.swagger.models.auth.In;
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
@RestController
@RequestMapping("admin/edu/teacher")
public class AdminTeacherController {

    @Autowired
    TeacherService teacherService;

    @GetMapping("list")
    public R listAllTeacher() {
        log.error("{}级别,当前时间{}:","error",new Date());
        log.warn("{}级别,当前时间{}:","warn",new Date());
        log.info("{}级别,当前时间{}:","info",new Date());
        log.debug("{}级别,当前时间{}:","debug",new Date());

        List<Teacher> list = teacherService.list();
        return R.ok().data("teacherList",list).setMessage("获取讲师列表成功");
    }

    @DeleteMapping("delete/{id}")
    public R deleteTeacherById(@PathVariable String id) {
        boolean b = teacherService.removeById(id);
        if (b){
            return R.ok().setMessage("删除成功");
        }
        return R.error().setMessage("删除失败");
    }
    @PostMapping("insert")
    public R saveTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save){
            return R.ok().setMessage("新增成功");
        }
        return  R.error().setMessage("新增失败");
    }
    @GetMapping("get/{id}")
    public R getTeacher(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().setMessage("获取讲师成功").data("teacher", teacher);
    }
    @PutMapping("update")
    public R updateTeacher(@RequestBody Teacher teacher){
        boolean b = teacherService.updateById(teacher);
        if (b){
            return R.ok().setMessage("更新成功");
        }
        return R.error().setMessage("更新失败");
    }
    @GetMapping("list/{pageNum}/{pageSize}")
    public R pageQuery(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        Page<Teacher> page = new Page<>();
        page.setCurrent(pageNum).setSize(pageSize);
        Page<Teacher> pageModel = teacherService.page(page);
        return  R.ok().data("pageModel", pageModel);
    }
}

