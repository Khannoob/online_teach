package com.sysu.guli.service.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.vod.service.MediaService;
import com.sysu.guli.service.vod.util.AliyunVodSDKUtils;
import com.sysu.guli.service.vod.util.VodProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MediaServiceImpl implements MediaService {
    String accessKeyId;
    String accessKeySecret;
    String workflowId;
    String regionId;
    @Autowired
    VodProperties vodProperties;

    @PostConstruct
    public void init() {
        this.accessKeyId = vodProperties.getKeyId();
        this.accessKeySecret = vodProperties.getKeySecret();
        this.workflowId = vodProperties.getWorkflowId();
        this.regionId = vodProperties.getRegionId();
    }

    @Override
    /**
     * 流式上传
     * @param keyId
     * @param keySecret
     * @param title 这里不是本地上传 title就是filename
     * @param fileName
     * @param inputStream
     */
    public String uploadVideo(InputStream is, String originalFilename) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, originalFilename, originalFilename, is);
        request.setWorkflowId(workflowId);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            String videoId = response.getVideoId();
            System.out.print("VideoId=" + videoId + "\n");
            return videoId;
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            log.error("视频上传失败,ErrorCode = {},ErrorMessage = {}", response.getCode(), response.getMessage());
            throw new GuliException(ResultCodeEnum.VIDEO_UPLOAD_ALIYUN_ERROR);
        }
    }

    /*获取播放凭证*/
    @Override
    public String getPlayAuth(String videoId) {
        /*初始化client对象*/
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(regionId, accessKeyId, accessKeySecret);
        /*获取播放凭证函数*/
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = AliyunVodSDKUtils.getVideoPlayAuth(client,videoId);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            log.error("视频播放失败ErrorMessage = {}", e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

        return response.getPlayAuth();
    }

    @Override
    public String getPlayUrl(String videoId) {
        /*初始化client对象*/
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(regionId, accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = AliyunVodSDKUtils.getPlayInfo(client,videoId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            GetPlayInfoResponse.PlayInfo playInfo = response.getPlayInfoList().get(0);
            //播放地址
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
            System.out.print("RequestId = " + response.getRequestId() + "\n");
            return playInfo.getPlayURL();
        } catch (Exception e) {
            log.error("视频播放失败ErrorMessage = {}", e.getLocalizedMessage());
            throw new GuliException(ResultCodeEnum.FETCH_PLAYAURl_ERROR);
        }

    }

}
