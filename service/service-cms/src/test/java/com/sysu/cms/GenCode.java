package com.sysu.cms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GenCode {
    @Test
    public void genCode() {

        String prefix = "";//用来拼接连接数据库的url地址数据库名称的、根据实际修改
        String moduleName = "cms";//用来拼接包名，当前模块名
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//工作所在的项目路径，可以使用绝对路径
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("khannoob");
        gc.setOpen(false); //生成后是否打开资源管理器
//        gc.setFileOverride(true); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");//默认Service接口会使用I开头，去掉首字母I
        gc.setIdType(IdType.ASSIGN_ID); //主键策略 使用雪花算法
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型使用java.util.Date
        gc.setSwagger2(true);//开启Swagger2模式:自动生成swagger注解
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://139.224.196.88:3306/" + prefix + "guli_" + moduleName + "?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("khan");
        dsc.setPassword("Yh199712");
        dsc.setDbType(DbType.MYSQL);//数据库类型
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName); //模块名
        pc.setParent("com.sysu.guli.service");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(moduleName + "_");//设置表前缀不生成

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setLogicDeleteFieldName("is_deleted");//逻辑删除字段名
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);//去掉布尔值的is_前缀

        //自动填充
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
        //设置BaseEntity
        strategy.setSuperEntityClass("com.sysu.guli.service.base.model.BaseEntity");
        // 填写BaseEntity中的公共字段
        strategy.setSuperEntityColumns("id", "gmt_create", "gmt_modified");
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}
