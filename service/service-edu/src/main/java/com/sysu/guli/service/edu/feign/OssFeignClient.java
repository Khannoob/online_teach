package com.sysu.guli.service.edu.feign;

import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.feign.fallback.OssFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
*  	1、在pom中引入 openfeign的场景启动器
	2、在启动类上添加@EnableFeignClients
	3、按照要访问的服务的接口  编写 feignclient接口
		@FeignClient("要访问的服务在注册中心中的名字") 注解标注到接口上
		将要访问的接口方法拷贝到当前feignclient接口中，删除方法体
		修改方法的映射地址 保证是方法的完整的访问地址
* */

@FeignClient(value = "service-oss",fallback = OssFeignClientFallBack.class) //等价于 http://localhost:8120
public interface OssFeignClient {

    @DeleteMapping("/admin/oss/file/delete") //和上面的地址进行拼接 使用路径参数传参
    public R deleteA(@RequestParam String module, @RequestParam String imgUrl);

}
