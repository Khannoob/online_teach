package com.sysu.guli.service.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors(chain = true)
public class BaseEntity {
    @ApiModelProperty(value = "ID")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
//    @JsonFormat(timezone = "CST", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间",hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
