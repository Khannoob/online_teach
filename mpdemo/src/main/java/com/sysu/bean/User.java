package com.sysu.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
//类的名称和表名一样 && 字段名和数据库字段一样

/**
 *
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user") //和不一样名字的表绑定 一样就省略
public class User {
    @TableId(value = "id",type = IdType.ASSIGN_ID) //用来绑定主键,type:表示主键id策略
    /**
     * ASSIGN_ID 使用雪花算法生成19位的id 毫秒级时间戳自增
     * ASSIGN_UUID 使用UUID生成随机32位id[不用,因为生成是无序的]
     * AUTO 数据库的自增长[数据库表必须设置自增长]
     * NONE 使用默认的[雪花算法]
     */
    private String uid;
    @TableField("name")//非主键绑定
    private String name;
    private Integer age;
    private String email;
    @TableField(value = "gmt_create",fill = FieldFill.INSERT_UPDATE)
    private Date gmtCreate;
    @TableField(value = "gmt_modified",fill = FieldFill.UPDATE)
    private Date gmtModified;
    @TableLogic
    private Integer deleted;
}
