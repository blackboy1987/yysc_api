
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.service.AdminService;
import com.bootx.util.IPUtils;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin/login")
public class LoginController extends BaseController {

	@Resource
	private AdminService adminService;

	@PostMapping
	public Result index(HttpServletRequest request,String username,String password) {
		Map<String,Object> data = new HashMap<>();
		Admin admin = adminService.findByUsername(username);
		if(admin==null){
			return Result.error("用户名或密码输入不正确");
		}
		if(admin.getIsLocked()){
			return Result.error("账号已被锁定");
		}
		if(!admin.getIsEnabled()){
			return Result.error("账号已被禁用");
		}
		if(!admin.isValidCredentials(password)){
			adminService.lock(admin);
			return Result.error("用户名或密码输入不正确");
		}

		String ip = IPUtils.getIpAddr(request);
		admin.setLastLoginDate(new Date());
		admin.setLastLoginIp(ip);
		admin.setLockDate(null);
		admin.setIsLocked(false);
		adminService.update(admin);
		adminService.unLock(admin);



		data.put("username",admin.getUsername());
		data.put("id",admin.getId());
		return Result.success(JWTUtils.create(admin.getId()+"",data));
	}
}