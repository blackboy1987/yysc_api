
package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.AppVersion;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Dic;
import com.bootx.service.DicCategoryService;
import com.bootx.service.DicService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/dic")
public class DicController extends BaseController {

	@Resource
	private DicService dicService;
	@Resource
	private DicCategoryService dicCategoryService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Dic dic) {
		dicService.save(dic);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Dic dic) {
		dicService.update(dic);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Audit(action = "项目查询")
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable) {
		return Result.success(dicService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {

		return Result.success();
	}
}