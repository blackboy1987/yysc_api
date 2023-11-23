package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.AdConfig;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Carousel;
import com.bootx.service.AdConfigService;
import com.bootx.service.CarouselService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminAdConfigController")
@RequestMapping("/api/admin/adConfig")
public class AdConfigController extends BaseController{

    @Resource
    private AdConfigService adConfigService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(AdConfig adConfig) {
        adConfigService.save(adConfig);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(AdConfig project) {
        adConfigService.update(project);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(adConfigService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {

        return Result.success();
    }

}
