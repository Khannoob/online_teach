package com.sysu.guli.service.edu.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class QueryTeacherBean {
    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "入驻开始时间")
    private String joinDateStart;

    @ApiModelProperty(value = "入驻结束时间")
    private String joinDateEnd;
}
