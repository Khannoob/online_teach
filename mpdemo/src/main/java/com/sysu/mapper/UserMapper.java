package com.sysu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sysu.bean.User;
import org.springframework.stereotype.Repository;

//userMapper继承BaseMapper时必须指定泛型-> 表对应的javaBean
@Repository
public interface UserMapper extends BaseMapper<User> {
}
