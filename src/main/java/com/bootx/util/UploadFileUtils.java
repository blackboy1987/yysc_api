package com.bootx.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UploadFileUtils {

    private static OSS client  = null;

    public static void upload(String path, File file){
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            OSSClient ossClient = new OSSClient(OSSConfig.endpoint_img, OSSConfig.accessKeyId_img, OSSConfig.accessKeySecret_img);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.length());
            ossClient.putObject(OSSConfig.bucketName_img, StringUtils.removeStart(path, "/"), inputStream, objectMetadata);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            try {
                FileUtils.delete(file);
            }catch (Exception ignored){

            }
        }
    }

    public static String getUrl(String path) {
        return OSSConfig.urlPrefix_img + path;
    }

    public static FileInfo getFileInfo(String fileUrl) {
        Map<String,Object> map = new HashMap<>();
        String result = WebUtils.get(fileUrl + "?x-oss-process=image/info", null);
        return JsonUtils.toObject(result,new TypeReference<FileInfo>(){});
    }
}
