package com.sysu;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.bean.Product;
import com.sysu.bean.User;
import com.sysu.mapper.ProductMapper;
import com.sysu.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MpdemoApplicationTests {

    @Autowired
    UserMapper userMapper;
    //1.查询所有
    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    //2.批量查询
    @Test
    void queryBatch() {
        Collection<? extends Serializable> idList  = Arrays.asList(1,2,3,4,5);
        userMapper.selectBatchIds(idList);
    }
    //3.待条件的查询
    @Test
    void queryConditions() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(User::getAge, 20).like(User::getName, "e");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }
    @Test
    void queryConditionsMap() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.lt(User::getAge, 26).like(User::getName, "e").or( i -> i.eq(User::getUid,2));
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }
    //4.分页查询

    /**
     * 1.使用userMapper.selectPage方法 传入分页参数 和查询条件
     * 2.配置类中注入Bean对象 PaginationInterceptor
     */
    @Test
    void queryPage() {
        Page<User> page = new Page<>(0,2);
        page = userMapper.selectPage(page, null);
        System.out.println("当前页码"+page.getCurrent());
        System.out.println("当前页条数"+page.getSize());
        System.out.println("总记录数"+page.getTotal());
        System.out.println("boolean有无下一页"+page.hasNext());
        System.out.println("boolean有无上一页"+page.hasPrevious());
        System.out.println("orderItem"+page.getOrders());
        System.out.println("查询到的分页数据集合"+page.getRecords());
    }
    //5.新增
    /*
      用户注册时 和以后修改信息时，可能需要查询自己什么时候注册或者更新
     */
    @Test
    void insertTest() {
        User user = new User();
        user.setName("zs");
        user.setAge(18);
        user.setEmail("Zs@qq.com");
        int i = userMapper.insert(user);
        System.out.println(i);
    }
    //6.删除
    @Test
    void deleteTest() {
        //物理删除  删除后不可恢复
        int i = userMapper.deleteById(2L);
        System.out.println(i);


    }
    @Test
    void logicDeleteTest() {
        //逻辑删除 增加字段deleted(不能写isDeleted)

        /**
         * 1.增加属性deleted 注解@TableLogic
         * 2.application.properties 加入以下配置，此为默认值，如果你的默认值和mp默认的一样,该配置可无
         */
        int i = userMapper.deleteById(2L);
        System.out.println(i);
        User user = userMapper.selectById(2L);
        System.out.println("user = " + user);

    }

    //7.更新
    @Test
    void updateByIdTest() {
        //实现新增和修改的时候记录时间
        /**
         *  1.在javaBean属性中添加gmt_create或者(gmt_Modified)属性,表中加入相应字段
         *  2.加上@TableField(value = "gmt_create",fill = FieldFill.INSERT_UPDATE)
         *  3.自定义拦截器实现MetaObjectHandler接口 重写insertFill 和 gmtCreate方法
         */
        User user = userMapper.selectById(1);
        user.setAge(18);
        userMapper.updateById(user);
    }
    @Autowired
    ProductMapper productMapper;
    @Test
    void highConcurrencyTest() {
        //A查询商品价格 100
        Product pA = productMapper.selectById(1L);
        //B查询商品价格 100
        Product pB = productMapper.selectById(1L);
        //A给价格增加50元 150
        pA.setPrice(pA.getPrice()+50);

        //B给价格减少30元 70
        pB.setPrice(pB.getPrice()-30);

        //A保存数据
        productMapper.updateById(pA);
        //B保存数据(实现乐观锁)
        /**
         * 1.在javaBean对象的version属性上加@version注解
         * 2.配置类中注入Bean对象 OptimisticLockerInterceptor
         */
        int i = productMapper.updateById(pB);
        //如果更新失败则重新查询更改
        if (i==0){
            pB = productMapper.selectById(1L);
            pB.setPrice(pB.getPrice()-30);
            productMapper.updateById(pB);
        }
        //最后的结果
        Product result = productMapper.selectById(1L);
        System.out.println("result = " + result);
    }
    //8.其他
    @Test
    public void testSelectCount() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age", 20, 30);

        Integer count = userMapper.selectCount(queryWrapper); //返回数据数量
        System.out.println(count);
    }
    //queryWrapper.inSql 子查询
    @Test
    public void testSelectObjs() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.in("id", 1, 2, 3);
        queryWrapper.inSql("id", "select id from user where id <= 3");

        List<Object> objects = userMapper.selectObjs(queryWrapper);//返回值是Object列表
        objects.forEach(System.out::println);
    }
    //最终的sql会合并 user.setAge()，以及 userUpdateWrapper.set()  和 setSql() 中 的字段
    @Test
    public void testUpdateSet() {

        //修改值
        User user = new User();

        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .set("name", "Tom")//可以使用set设置修改的字段
                .set("age", 60)
                .setSql(" email = '123@qq.com'")//可以有子查询
                .like("name", "o");

        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }

}
