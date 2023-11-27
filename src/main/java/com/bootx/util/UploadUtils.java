package com.bootx.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UploadUtils {


    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String bucketName = "bootx-tuchuang";
    private static final String accessKeyId = "LTAI5tGYCpDRGU1s3VZjydcz";
    private static final String secretAccessKey = "lgTgxgtkaG4RDgxqwDbzk4W82VPUGG";
    private static final String urlPrefix = "https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/";

    public static String upload(String path, File file) throws Exception {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,secretAccessKey);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, FileUtils.openInputStream(file));
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            FileUtils.delete(file);
        }
        return urlPrefix+path;
    }
}