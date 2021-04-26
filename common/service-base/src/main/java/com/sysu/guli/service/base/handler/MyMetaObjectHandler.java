package com.sysu.guli.service.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("gmtModified", new Date());
        metaObject.setValue("gmtCreate", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("gmtModified", new Date());
    }
}
