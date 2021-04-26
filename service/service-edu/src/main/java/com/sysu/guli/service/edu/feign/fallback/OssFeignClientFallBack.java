package com.sysu.guli.service.edu.feign.fallback;

import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.edu.feign.OssFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OssFeignClientFallBack implements OssFeignClient {

    @Override
    public R deleteA(String module, String imgUrl) {
        log.error("头像删除失败!");
        return R.error().setMessage("文件删除失败,稍候重试!");
    }
}
