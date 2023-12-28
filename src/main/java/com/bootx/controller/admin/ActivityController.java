package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Activity;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.security.CurrentUser;
import com.bootx.service.ActivityService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminActivityController")
@RequestMapping("/api/admin/activity")
public class ActivityController extends BaseController{

    @Resource
    private ActivityService activityService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Activity activity, @CurrentUser Admin admin) {
        activityService.save(activity);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(Activity activity) {
        Activity parent = activityService.find(activity.getId());
        Admin current = adminService.getCurrent();
        if(parent==null ){
            return Result.error("项目不存在");
        }
        activityService.update(activity);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(activityService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {

        return Result.success();
    }

}
