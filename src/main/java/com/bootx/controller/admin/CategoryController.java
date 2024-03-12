package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.CacheKey;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Category;
import com.bootx.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminCategoryController")
@RequestMapping("/api/admin/category")
public class CategoryController extends BaseController{

    @Resource
    private CategoryService categoryService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Category category) {
        categoryService.save(category);
        redisService.delete(CacheKey.API_CATEGORY_LIST);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(Category category) {
        Category parent = categoryService.find(category.getId());
        parent.setName(category.getName());
        parent.setOrder(category.getOrder());
        categoryService.update(parent);
        redisService.delete(CacheKey.API_CATEGORY_LIST);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "项目查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Long parentId,String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id,name,orders,createdDate,parent_id parentId from category where 1=1");
        if(StringUtils.isNotBlank(name)){
            sb.append(" and name like '%").append(name).append("%'");
        }
        if(parentId!=null&&parentId>0){
            sb.append(" and parent_id  = ").append(parentId);
        }else{
            sb.append(" and parent_id is null");
        }
        sb.append(" order by orders asc");
        return Result.success(jdbcTemplate.queryForList(sb.toString()));
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long[] ids) {

        return Result.success();
    }

    @PostMapping("/tree")
    public Result tree() {
        return Result.success(jdbcTemplate.queryForList("select id `key`,name `title` from category where parent_id is null order by orders asc;"));
    }
}
