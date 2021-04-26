package com.sysu.guli.service.cms.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.cms.entity.AdType;
import com.sysu.guli.service.cms.service.AdService;
import com.sysu.guli.service.cms.service.AdTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/cms/ad-type")
public class AdminAdTypeController {
    @Autowired
    AdTypeService adTypeService;

    @Autowired
    RedisTemplate redisTemplate;
    

    //新增广告分类
    @PostMapping("save")
    public R saveAd(@RequestBody AdType adType){
        adTypeService.save(adType);
        return R.ok();
    }
    //删除广告分类
    @DeleteMapping("delete/{id}")
    public R deleteAd(@PathVariable String id){
        adTypeService.removeById(id);
        return R.ok();
    }
    //更新广告分类
    @PutMapping("update")
    public R updateAd(@RequestBody AdType adType){
        adTypeService.updateById(adType);
        return R.ok();
    }
    //查询指定广告分类
    @GetMapping("get/{id}")
    public R getAd(@PathVariable String id){
        AdType adType = adTypeService.getById(id);
        return R.ok().data("item", adType);
    }
    //查询广告分类分页
    @GetMapping("list/{pageNum}/{pageSize}")
    public R listPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
        Page<AdType> page = new Page<>(pageNum, pageSize);
        page = adTypeService.page(page, null);
        return R.ok().data("page", page);
    }
}

