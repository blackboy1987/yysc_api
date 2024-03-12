package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Soft;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("AdminSoftController")
@RequestMapping("/api/admin/soft")
public class SoftController extends BaseController{

    @Resource
    private SoftService softService;

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Soft soft) {
        softService.save(soft);
        return Result.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public Result update(Soft soft) {
        softService.update(soft);
        return Result.success();
    }

    /**
     * 审核通过
     */
    @Audit(action = "审核通过")
    @PostMapping("/pass")
    public Result pass(Long ids) {
        Soft soft = softService.find(ids);
        soft.setStatus(1);
        softService.update(soft);
        return Result.success();
    }

    /**
     * 审核拒绝
     */
    @Audit(action = "审核拒绝")
    @PostMapping("/reject")
    public Result reject(Long ids) {
        Soft soft = softService.find(ids);
        soft.setStatus(2);
        softService.update(soft);
        return Result.success();
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "软件查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Long categoryId, String name, Pageable pageable,Integer status) {
        StringBuilder querySql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        querySql.append("select soft.id,soft.name,soft.createdDate,soft.logo,soft.status,member.username username from soft,soft_categories,member where soft.member_id=member.id and soft_categories.softs_id=soft.id");
        countSql.append("select count(soft.id) from soft,soft_categories where soft_categories.softs_id=soft.id");
        if(StringUtils.isNotBlank(name)){
            querySql.append(" and soft.name like '%").append(name).append("%'");
            countSql.append(" and soft.name like '%").append(name).append("%'");
        }
        if(status!=null){
            querySql.append(" and soft.status=").append(status);
            countSql.append(" and soft.status=").append(status);
        }
        if(categoryId!=null){
            querySql.append(" and soft_categories.categories_id=").append(categoryId);
            countSql.append(" and soft_categories.categories_id=").append(categoryId);
        }
        querySql.append(" order by soft.createdDate desc");
        querySql.append(" limit ?,?");
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(querySql.toString(), (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
        Long count = jdbcTemplate.queryForObject(countSql.toString(),Long.class);
        return Result.success(new Page<>(maps,count!=null?count:0L,pageable));
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
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id `key`,name `title` from category where parent_id is null order by orders asc;");
        maps.forEach(item->{
            item.put("children",jdbcTemplate.queryForList("select id `key`,name `title` from category where parent_id =? order by orders asc;",item.get("key")));
        });
        return Result.success(maps);
    }
}
