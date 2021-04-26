package com.sysu.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.sysu.entity.Student;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EasyExcelTest {
    String filePath ="D:/excel/test1.xlsx";
    @Test
    public void testRead(){
        EasyExcel.read(filePath)
                .password("123")
                .head(Student.class)
                .autoCloseStream(true)
                .sheet(0)
                .registerReadListener(new EasyExcelListener())
                .doRead();
    }
    @Test
    public void testWrite(){

        EasyExcel.write(filePath).excelType(ExcelTypeEnum.XLSX).autoCloseStream(true)
                .password("123").sheet("sheet01")
                .head(Student.class)
                .doWrite(data());
    }
    public List<Student> data(){
        ArrayList<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 66000; i++) {
            Student student = new Student();
            student.setName("郑维圣"+i);
            student.setBirthday(new Date());
            student.setSalary(RandomUtils.nextDouble(20000, 30000));
            list.add(student);
        }
        
        
        return list;
    }

}
