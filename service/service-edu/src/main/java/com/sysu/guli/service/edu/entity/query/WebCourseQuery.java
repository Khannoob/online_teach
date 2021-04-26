package com.sysu.guli.service.edu.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程查询对象")
public class WebCourseQuery {
    @ApiModelProperty(value = "一级分类ID")
    private String subjectParentId;
    @ApiModelProperty(value = "二级分类ID")
    private String subjectId;
    @ApiModelProperty(value = "按销售量排序")
    private String buyCountSort;
    @ApiModelProperty(value = "按发布时间排序")
    private String publishTimeSort;
    @ApiModelProperty(value = "按价格排序")
    private String priceSort;
    @ApiModelProperty(value = "价格排序方式")
    private Integer type; //价格正序：1，价格倒序：2
}