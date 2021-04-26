package com.sysu.guli.service.vod.controller.api;

import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.helper.JwtHelper;
import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.vod.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RestController

@RequestMapping("/api/vod/media")
@Slf4j

public class ApiMediaController {
    @Autowired
    MediaService mediaService;

    @GetMapping("auth/get-play-auth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId, HttpServletRequest request){

        JwtHelper.checkToken(request);
        String playAuth =  mediaService.getPlayAuth(videoId);
        return R.ok().data("playAuth",playAuth);
    }

    @GetMapping("get-play-url/{videoId}")
    public R getPlayUrl(@PathVariable String videoId){
        String getPlayUrl =  mediaService.getPlayUrl(videoId);
        return R.ok().data("getPlayUrl",getPlayUrl);
    }

}
