
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.service.AdminService;
import com.bootx.service.MemberService;
import com.bootx.util.IPUtils;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberLoginController")
@RequestMapping("/api/member/login")
public class LoginController{

	@Resource
	private MemberService memberService;

	@PostMapping
	public Result index(HttpServletRequest request,String username,String password) {
		Map<String,Object> data = new HashMap<>();
		Member member = memberService.findByUsername(username);
		if(member==null){
			return Result.error("用户名或密码输入不正确");
		}
		if(member.getIsLocked()){
			return Result.error("账号已被锁定");
		}
		if(!member.getIsEnabled()){
			return Result.error("账号已被禁用");
		}
		if(!member.isValidCredentials(password)){
			memberService.lock(member);
			return Result.error("用户名或密码输入不正确");
		}

		String ip = IPUtils.getIpAddr(request);
		member.setLastLoginDate(new Date());
		member.setLastLoginIp(ip);
		member.setLockDate(null);
		member.setIsLocked(false);
		if(StringUtils.isBlank(member.getEmail())){
			member.setEmail(member.getUsername()+"@qq.com");
		}
		memberService.update(member);
		memberService.unLock(member);



		data.put("username",member.getUsername());
		data.put("id",member.getId());
		data.put("token",JWTUtils.create(member.getId()+"",data));
		return Result.success(data);
	}
}