
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdminService;
import com.bootx.service.MemberService;
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
@RestController("MemberIndexController")
@RequestMapping("/api/member")
public class IndexController extends BaseController {

	@Resource
	private MemberService memberService;

	@PostMapping("/currentUser")
	public Result currentUser() {
		Member member = memberService.getCurrent();
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		return Result.success(data);
	}

	@PostMapping("/index")
	public Result index(@CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		return Result.success(data);
	}
}