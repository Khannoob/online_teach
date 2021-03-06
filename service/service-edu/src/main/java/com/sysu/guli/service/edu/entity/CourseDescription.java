package com.sysu.guli.service.edu.entity;

import com.sysu.guli.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author khannoob
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_course_description")
@ApiModel(value="CourseDescription对象", description="课程简介")
public class CourseDescription extends BaseEntity {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "ID")
    @TableId(value = "id",type = IdType.NONE)//自己指定id为courseId 不要自动生成
    private String id;

    @ApiModelProperty(value = "课程简介")
    private String description;


}
