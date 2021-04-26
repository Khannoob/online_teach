package com.sysu.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @TableId("id")
    private String pid;
    private String name;
    private Integer price;

    @Version()
    private Integer version;
}
