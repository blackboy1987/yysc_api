
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin")
public class IndexController extends BaseController {

	@Resource
	private AdminService adminService;

	@PostMapping("/currentUser")
	public Result currentUser(HttpServletRequest request) {
		String token = request.getHeader("token");
		Admin admin = adminService.getCurrent();
		Map<String,Object> data = new HashMap<>();
		data.put("username",admin.getUsername());
		return Result.success(data);
	}
}