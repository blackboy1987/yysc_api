package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ApiAppVersionController")
@RequestMapping("/api/appVersion")
public class AppVersionController extends BaseController {

   @PostMapping("/check")
    public Result check(){
        return Result.success(jdbcTemplate.queryForList("select versionCode,downloadUrl,memo from AppVersion order by createdDate desc limit 1"));
    }

}
