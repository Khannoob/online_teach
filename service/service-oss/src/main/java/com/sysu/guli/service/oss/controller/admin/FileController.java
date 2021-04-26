package com.sysu.guli.service.oss.controller.admin;

import com.sysu.guli.service.base.result.R;
import com.sysu.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "阿里云文件管理")
 //跨域
@RestController
@Slf4j
@RequestMapping("/admin/oss/file")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("upload")
    public R uploadAvatar(MultipartFile file, String module){
        String imgUrl = fileService.upload(file,module);
        return R.ok().data("imgUrl", imgUrl);
    }
    @DeleteMapping("delete")
    public R deleteA(@RequestParam String module, @RequestParam String imgUrl){
        System.out.println("module = " + module);
        System.out.println("imgUrl = " + imgUrl);
        fileService.delete(module,imgUrl);
        return R.ok();
    }
}
