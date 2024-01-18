
package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/admin")
public class AdminController extends BaseController {

	@Resource
	private AdminService adminService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(Admin admin) {
		adminService.save(admin);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(Admin admin) {
		adminService.save(admin);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	public Result list(Pageable pageable) {
		return Result.success(adminService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		adminService.delete(ids);
		return Result.success();
	}

}