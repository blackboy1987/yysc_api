
package com.bootx.controller.api;

import com.bootx.common.CacheKey;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.CategoryService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("ApiCategoryController")
@RequestMapping("/api/category")
public class CategoryController extends BaseController {


	/**
	 * 分类列表
	 * @return
	 */
	@PostMapping("/list")
	@JsonView(BaseEntity.PageView.class)
	public Result list() {
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = JsonUtils.toObject(redisService.get(CacheKey.API_CATEGORY_LIST), new TypeReference<List<Map<String, Object>>>() {
			});
		}catch (Exception e){
			e.printStackTrace();
		}
		if(list.isEmpty()){
			list = jdbcTemplate.queryForList("select id,name from Category where parent_id is not null");
			redisService.set(CacheKey.API_CATEGORY_LIST, JsonUtils.toJson(list));
		}
		return Result.success(list);
	}
}