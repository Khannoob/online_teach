package com.sysu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Student {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("生日")
    @DateTimeFormat("yyyy年MM月dd日")
    private Date birthday;
    @DateTimeFormat("薪水")
    @NumberFormat("#.##")//保留两位小数
    private double salary;
}
