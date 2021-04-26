package com.sysu.guli.service.edu.controller.admin;


import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.entity.Subject;
import com.sysu.guli.service.edu.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */

/**
 * 	后端课程分类导入请求的处理：用户将课程分类的excel文件上传，代码需要将数据解析存到数据库(easyexcel)
 * 		1、创建一个接口接受上传的excel文件
 * 		2、引入easyexcel依赖
 * 		3、为excel每一行的数据封装一个javabean
 * 		4、创建监听器：回调方法中处理每一行读取到的数据
 * 		5、使用easyexcel开始读取文件中的数据并设置监听器
 */

@RestController
@Slf4j
@RequestMapping("/admin/edu/subject")
public class AdminSubjectController {
    @Autowired
    SubjectService subjectService;
    @GetMapping("nested-list")
    public R nestedList(){
        List<Subject> subjectList = subjectService.getNestedList();
        return R.ok().data("items", subjectList);//规范为items
    }
    @PostMapping("import")
    public R batchImport(MultipartFile subjects){
            subjectService.batchImport(subjects);
            return R.ok().setMessage("批量导入成功!");
    }
}

