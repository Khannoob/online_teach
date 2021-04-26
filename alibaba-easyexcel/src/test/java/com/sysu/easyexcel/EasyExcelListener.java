package com.sysu.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sysu.entity.Student;

import java.util.ArrayList;

public class EasyExcelListener extends AnalysisEventListener<Student> {
    ArrayList list = new ArrayList<Student>();
    int i = 0;
    int limit = 1000;
    public void invoke(Student student, AnalysisContext analysisContext) {
        list.add(student);
        if (list.size() == limit){
            System.out.println("list = " + list);
            System.out.println("次数 = "+(++i));
            list.clear();
        }
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size()>0){
            System.out.println("解析完毕,只剩下最后的一点数据");
            System.out.println("list = " + list);
        }
    }
}
