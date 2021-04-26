package com.sysu.guli.service.edu.service;

import com.sysu.guli.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
public interface SubjectService extends IService<Subject> {



    void batchImport(MultipartFile subjects);

    List<Subject> getNestedList();
}
