package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.AppVersion;
import com.bootx.entity.BaseEntity;
import com.bootx.service.AppVersionService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminAppVersionController")
@RequestMapping("/api/admin/appVersion")
public class AppVersionController extends BaseController{

    @Resource
    private AppVersionService appVersionService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(AppVersion appVersion) {
        appVersionService.save(appVersion);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(AppVersion appVersion) {
        appVersionService.update(appVersion);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(appVersionService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {

        return Result.success();
    }

}
