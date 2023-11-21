
package com.bootx.controller.admin;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/admin")
public class AdminController extends BaseController {

	@Resource
	private AdminService adminService;

	/**
	 * 检查用户名是否存在
	 */
	@GetMapping("/check_username")
	public @ResponseBody boolean checkUsername(String username) {
		return StringUtils.isNotEmpty(username) && !adminService.usernameExists(username);
	}


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
	@GetMapping("/list")
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