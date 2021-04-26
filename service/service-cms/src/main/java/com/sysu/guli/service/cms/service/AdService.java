package com.sysu.guli.service.cms.service;

import com.sysu.guli.service.cms.entity.Ad;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 广告推荐 服务类
 * </p>
 *
 * @author khannoob
 * @since 2021-04-21
 */
public interface AdService extends IService<Ad> {

    Map<String, Object> getAdsByType();
}
