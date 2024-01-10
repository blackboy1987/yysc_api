package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.HotSearch;
import com.bootx.service.HotSearchService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminHotSearchController")
@RequestMapping("/api/admin/hotSearch")
public class HotSearchController extends BaseController{

    @Resource
    private HotSearchService hotSearchService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(HotSearch hotSearch) {
        hotSearchService.save(hotSearch);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(HotSearch hotSearch) {
        hotSearchService.update(hotSearch);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(hotSearchService.findPage(pageable));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {
        hotSearchService.delete(ids);
        return Result.success();
    }

}
