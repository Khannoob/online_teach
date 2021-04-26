package com.sysu.guli.service.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String upload(MultipartFile file, String module);

    void delete(String module, String imgUrl);
}
