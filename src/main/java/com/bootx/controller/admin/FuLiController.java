
package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.FuLi;
import com.bootx.service.FuLiService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("adminFuLiController")
@RequestMapping("/api/admin/fuLi")
public class FuLiController extends BaseController {

	@Resource
	private FuLiService fuLiService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(FuLi fuLi) {
		fuLiService.save(fuLi);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(FuLi fuLi) {
		fuLiService.update(fuLi);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Audit(action = "项目查询")
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable) {
		return Result.success(fuLiService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		fuLiService.delete(ids);
		return Result.success();
	}
}