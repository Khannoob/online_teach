package com.sysu.guli.service.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MediaService {


    String uploadVideo(InputStream is, String originalFilename);

    String getPlayAuth(String videoId);

    String getPlayUrl(String videoId);
}
