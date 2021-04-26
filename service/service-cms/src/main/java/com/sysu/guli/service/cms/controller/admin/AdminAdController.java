package com.sysu.guli.service.cms.controller.admin;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.cms.entity.Ad;
import com.sysu.guli.service.cms.service.AdService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 广告推荐 前端控制器
 * 管理员进行 CRUD
 * </p>
 *
 * @author khannoob
 * @since 2021-04-21
 */

@RestController
@RequestMapping("/admin/cms/ad")
public class AdminAdController {
    @Autowired
    AdService adService;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //根据分类id获取广告集合并排序
    @GetMapping("getAdsByType")
    public R getAdsByType(){
        Map<String,Object> adMap = adService.getAdsByType();
        return R.ok().data(adMap);
    }


/*    @GetMapping("get")
    public R testGet(){
        String s = stringRedisTemplate.opsForValue().get("srt");
        Gson gson = new Gson();
        Ad srt = gson.fromJson(s, Ad.class);
        System.out.println(srt);

        System.out.println(redisTemplate.opsForValue().get("rt"));
        return R.ok();
    }

    @GetMapping("set")
    public R testSet(){
        Ad ad = new Ad();
        ad.setColor("green");
        ad.setTitle("test");
        Gson gson = new Gson();
        stringRedisTemplate.opsForValue().set("srt", gson.toJson(ad), 50,TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("rt", ad, 50,TimeUnit.SECONDS);
        return R.ok();
    }*/

    //新增广告
    @PostMapping("save")
    public R saveAd(@RequestBody Ad ad){
        adService.save(ad);
        return R.ok();
    }
    //删除广告
    @DeleteMapping("delete/{id}")
    public R deleteAd(@PathVariable String id){
        adService.removeById(id);
        return R.ok();
    }
    //更新广告
    @PutMapping("update")
    public R updateAd(@RequestBody Ad ad){
        adService.updateById(ad);
        return R.ok();
    }
    //查询指定
    @GetMapping("get/{id}")
    public R getAd(@PathVariable String id){
        Ad ad = adService.getById(id);
        return R.ok().data("item", ad);
    }
    //查询广告分页
    @GetMapping("list/{pageNum}/{pageSize}")
    public R listPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        Page<Ad> page = new Page<>(pageNum, pageSize);
        page = adService.page(page, null);
        return R.ok().data("page", page);
    }
}

