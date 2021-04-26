package com.sysu.guli.service.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelSubjectData {
    @ExcelProperty("一级分类")
    private String level1Title;
    @ExcelProperty("二级分类")
    private String level2Title;
}
