
package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.DicCategory;
import com.bootx.service.DicCategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/dicCategory")
public class DicCategoryController extends BaseController {

	@Resource
	private DicCategoryService dicCategoryService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(DicCategory dicCategory) {
		dicCategoryService.save(dicCategory);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(DicCategory dicCategory) {
		dicCategoryService.update(dicCategory);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Audit(action = "项目查询")
	public Result list() {
		return Result.success(jdbcTemplate.queryForList("select id `key`,name title from diccategory order by orders asc"));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		dicCategoryService.delete(ids);
		return Result.success();
	}
}