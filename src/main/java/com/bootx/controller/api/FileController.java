package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.util.UploadFileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String path = "yysc/app/"+ UUID.randomUUID().toString().replace("-","")+"."+ FilenameUtils.getExtension(file.getOriginalFilename());
        File temp = new File(SystemUtils.getJavaIoTmpDir(),path);
        if(!temp.getParentFile().exists()){
            temp.getParentFile().mkdirs();
        }
        file.transferTo(temp);
        UploadFileUtils.upload(path, temp);
        Map<String,Object> data = new HashMap<>();
        data.put("url",UploadFileUtils.getUrl(path));
        return Result.success(data);
    }

}
