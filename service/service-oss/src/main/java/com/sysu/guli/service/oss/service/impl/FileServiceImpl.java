package com.sysu.guli.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.sysu.guli.service.base.exception.GuliException;
import com.sysu.guli.service.base.result.ResultCodeEnum;
import com.sysu.guli.service.oss.service.FileService;
import com.sysu.guli.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssProperties ossProperties;
    @PostConstruct //java8 提供的初始化注解 在项目启动的时候只调用一次
    public void init(){
        this.scheme = ossProperties.getScheme();
        this.endpoint = ossProperties.getEndpoint();
        this.accessKeyId = ossProperties.getAccessKeyId();
        this.accessKeySecret = ossProperties.getAccessKeySecret();
        this.bucketName = ossProperties.getBucketName();
    }
    String endpoint;
    String scheme;
    String accessKeyId;
    String accessKeySecret;
    String bucketName;
    @Override
    public String upload(MultipartFile file, String module){
        String imgUrl = null;
        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replace("-", "")+
                    originalFilename.substring(originalFilename.lastIndexOf("."));
            String folder = new DateTime().toString("/yyyy/MM/dd/");
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);
            // 创建PutObjectRequest对象。
            // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            //Object 路径(key)代表文件的储存地址和上传后的文件名
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, module+folder+fileName, file.getInputStream());
            // 上传文件。
            ossClient.putObject(putObjectRequest);
            // 关闭OSSClient。
            ossClient.shutdown();

            //返回文件的下载路径
            //"https://khan-online-edu.oss-cn-shanghai.aliyuncs.com/avatar/2021/04/13/e83a9b88033e4d1b8de4b20ef73444c3.jpg"
            imgUrl = scheme+bucketName+"."+endpoint+"/"+module+folder+fileName;
            return imgUrl;
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public void delete(String module, String imgUrl) {
        String objectName = imgUrl.substring(imgUrl.lastIndexOf(module+"/"));
        // 创建OSSClient实例。
        try {
            OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);
            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, objectName);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
//            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.FILE_DELETE_ERROR);
        }

    }
}
