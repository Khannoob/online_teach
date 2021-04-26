package com.sysu.guli.service.vod.controller.admin;

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
import javax.servlet.http.HttpSession;
import java.io.InputStream;

@RestController

@RequestMapping("/admin/vod/media")
@Slf4j

public class AdminMediaController {
    @Autowired
    MediaService mediaService;
    @PostMapping("upload")
    public R uploadVideo(@RequestParam("file")MultipartFile file){
        String videoId = null;
        try {
            InputStream is = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            videoId = mediaService.uploadVideo(is,originalFilename);

            return R.ok().data("videoId", videoId);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }


}
