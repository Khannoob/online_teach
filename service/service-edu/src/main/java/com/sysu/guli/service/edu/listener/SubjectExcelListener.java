package com.sysu.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sysu.guli.service.edu.entity.Subject;
import com.sysu.guli.service.edu.entity.excel.ExcelSubjectData;
import com.sysu.guli.service.edu.mapper.SubjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//因为当前类不是组件类所以不能直接@AutoWired自动装载,当然也可以使用@Service注解使当前类成为组件类
//这里我们采用了构造器传入一个serviceMapper的方式
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;
    //创造一个当前类的构造器 使service层在调用此方法的时候需要传一个 baseMapper实例 用于查询数据库
    public SubjectExcelListener(SubjectMapper subjectMapper){
        this.subjectMapper = subjectMapper;
    }
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //每加载一行数据 就存一次数据库
        String level1Title = excelSubjectData.getLevel1Title();
        String level2Title = excelSubjectData.getLevel2Title();
        /**
         * 1. 查询一级标题是否存在 [title 相等 && pid==0] selectOne
         * 2.1 存在-->拿到一级标题的pid
         * 2.2 不存在-->新增一级标题,并获得新创建一级标题的pid
         * 3. 查询二级标题是否存在 [title 相等 && pid相等]selectCount
         * 4.1 不存在-->新增二级标题
         * 4.2 存在-->不操作
         */
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getTitle,level1Title);
        queryWrapper.eq(Subject::getParentId,"0");
        //查询一级标题是否存在
        Subject level1Subject = subjectMapper.selectOne(queryWrapper);
        if (level1Subject==null){
            //不存在--->创建新的一级标题
            level1Subject = new Subject();
            level1Subject.setParentId("0");
            level1Subject.setTitle(level1Title);
            subjectMapper.insert(level1Subject);
        }
        //查询二级标题是否存在
        queryWrapper.clear();//清空之前的查询条件重新设置
        queryWrapper.eq(Subject::getParentId,level1Subject.getId());
        queryWrapper.eq(Subject::getTitle,level2Title);
        Integer count = subjectMapper.selectCount(queryWrapper);
        //不存在--->创建新的二级标题
        if (count==0){
            Subject level2Subject = new Subject();
            level2Subject.setTitle(level2Title);
            level2Subject.setParentId(level1Subject.getId());
            subjectMapper.insert(level2Subject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("课程分类excel处理完毕..");
    }
}
