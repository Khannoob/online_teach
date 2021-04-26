package com.sysu.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.edu.entity.Subject;
import com.sysu.guli.service.edu.entity.excel.ExcelSubjectData;
import com.sysu.guli.service.edu.listener.SubjectExcelListener;
import com.sysu.guli.service.edu.mapper.SubjectMapper;
import com.sysu.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {


    @Override
    public void batchImport(MultipartFile subjects) {
        try {
            EasyExcel.read(subjects.getInputStream())
                    .excelType(ExcelTypeEnum.XLS)
                    .head(ExcelSubjectData.class)
                    .sheet(0)
                    .registerReadListener(new SubjectExcelListener(baseMapper))
                    .doRead();
        } catch (Exception e) {
//            e.printStackTrace();
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @Override
    public List<Subject> getNestedList() {
        //业务里面实现分类 parent和child分开
        HashMap<String, Subject> level1SubjectMap = new HashMap<>();
        //第一次遍历得到所有的一级title
        List<Subject> subjectList = baseMapper.selectList(null);
        for (Subject level1Subject : subjectList) {
            if ("0".equals(level1Subject.getParentId())){
//                level1Subjects.add(subject); 用map key为id方便调用
                level1SubjectMap.put(level1Subject.getId(), level1Subject);
            }
        }
        //第二次遍历拿到所有的二级title
        for (Subject level2Subject : subjectList) {
            if (!"0".equals(level2Subject.getParentId())){
                //设置一级title的属性 通过pid从map里面获取pSubject
                Subject level1Subject = level1SubjectMap.get(level2Subject.getParentId());
                if (level1Subject!=null){
                    level1Subject.getChildren().add(level2Subject);
                }
            }
        }

        return new ArrayList<Subject>(level1SubjectMap.values());
    }
}
