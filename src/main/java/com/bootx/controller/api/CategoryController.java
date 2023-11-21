
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.CategoryService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiCategoryController")
@RequestMapping("/api/category")
public class CategoryController extends BaseController {

	@Resource
	private CategoryService categoryService;

	@PostMapping("/list")
	@JsonView(BaseEntity.PageView.class)
	public Result list() {
		return Result.success(jdbcTemplate.queryForList("select id,name from Category"));
	}
}