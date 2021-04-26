package com.sysu.guli.service.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sysu.guli.service.cms.entity.Ad;
import com.sysu.guli.service.cms.entity.AdType;
import com.sysu.guli.service.cms.mapper.AdMapper;
import com.sysu.guli.service.cms.service.AdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sysu.guli.service.cms.service.AdTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告推荐 服务实现类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-21
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {
    @Cacheable(value = "ad",key = "'getAdsByType'")
    //@Cacheable(value = "ad",key = "'getAdsByType'") 拼接key ad::getAdsByType redis推荐单词使用: 连接,redis管理工具自动按照:分文件夹管理键
    @Override
    public Map<String, Object> getAdsByType() {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        List<Ad> ads = baseMapper.selectList(queryWrapper);
        ArrayList<Ad> bannerList = new ArrayList<>();
        ArrayList<Ad> courseList = new ArrayList<>();
        ArrayList<Ad> teacherList = new ArrayList<>();
        for (Ad ad : ads) {
            if ("1".equals(ad.getTypeId())){
                bannerList.add(ad);
            }
            if ("3".equals(ad.getTypeId())){
                courseList.add(ad);
            }
            if ("2".equals(ad.getTypeId())){
                teacherList.add(ad);
            }
        }
        map.put("bannerList",bannerList);
        map.put("courseList",courseList);
        map.put("teacherList",teacherList);

        return map;
    }
}
