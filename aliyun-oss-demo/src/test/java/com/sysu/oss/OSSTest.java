package com.sysu.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class OSSTest {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private String scheme = "https://";
    private String endpoint = "oss-cn-shanghai.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private String accessKeyId = "LTAI5tGSRa1K5vadpHPEhh2v";
    private String accessKeySecret = "y0ehc54nPp3cqUMzZbYQYHPnX7XegP";
    private String bucketName = "khan-online-edu";
    private String filePath = "C:/Users/53003/Desktop/07.jpg";
    private String fileName = UUID.randomUUID().toString().replace("-", "")+filePath.substring(filePath.lastIndexOf("."));
    private String module = "avatar";
    private String folder = "/"+new DateTime().toString("yyyy/MM/dd/");


    @Before
    public void testBefore(){

    }
    @Test
    public void testDelete(){
        String imgUrl = "https://khan-online-edu.oss-cn-shanghai.aliyuncs.com/avatar/2021/04/13/e83a9b88033e4d1b8de4b20ef73444c3.jpg";

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String objectName = imgUrl.substring(imgUrl.lastIndexOf("avatar/"));

    // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);

    // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

    // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void testHttpClient() throws IOException {
        //1.创建HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.发起Http请求-GET方式
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        //new HttpPost POST请求
        //3.对象执行请求
        CloseableHttpResponse response = httpclient.execute(httpGet);
        //4.获取Response对象-拿到entity响应体
        HttpEntity entity = response.getEntity();
        System.out.println("entity = " + EntityUtils.toString(entity,"UTF-8"));
    }


    @Test
    public void testString(){
        System.out.println("scheme+endpoint = " + scheme+endpoint);
        System.out.println("bucketName = " + bucketName);
        System.out.println("key = "+module+folder+fileName);
    }

    @Test
    public void testUpload(){


        // 创建OSSClient实例。
                OSS ossClient = new OSSClientBuilder().build(scheme+endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        //Object 路径(key)代表文件的储存地址和上传后的文件名
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, module+folder+fileName, new File(filePath));
        // 上传文件。
                ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
                ossClient.shutdown();
    }
}
