package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.security.CurrentUser;
import com.bootx.util.UploadUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController("AdminFileController")
@RequestMapping("/api/admin/file")
public class FileController extends BaseController{

    @PostMapping("/upload")
    public Result upload(MultipartFile file, @CurrentUser Admin admin) throws Exception {
        String fileName = UUID.randomUUID().toString().replace("-","")+ FilenameUtils.getExtension(file.getName());
        File tmp = new File(SystemUtils.getJavaIoTmpDir(), UUID.randomUUID().toString().replace("-","")+"_"+file.getName());
        file.transferTo(tmp);

        return Result.success(UploadUtils.upload(fileName,tmp));
    }


}
