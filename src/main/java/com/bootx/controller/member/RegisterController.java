
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.service.MemberRankService;
import com.bootx.service.MemberService;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("memberRegisterController")
@RequestMapping("/api/member/register")
public class RegisterController extends BaseController {

	@Resource
	private MemberService memberService;

	@Resource
	private MemberRankService memberRankService;

	/**
	 * 注册提交
	 */
	@PostMapping
	public Result submit(String username, String password, String email, String spreadMemberUsername, HttpServletRequest request) {
		if (memberService.usernameExists(username)) {
			return Result.error("用户名已存在");
		}
		if (memberService.emailExists(email)) {
			return Result.error("邮箱已存在");
		}

		Member member = new Member();
		member.setUsername(username);
		member.setPassword(password);
		member.setEmail(email);
		member.setPoint(0L);
		member.setRemainPoint(0L);
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLockDate(null);
		member.setContinuousSignInDays(0);
		member.setSignInDate(null);
		member.setSignInDays(0);
		member.setLastLoginIp(request.getRemoteAddr());
		member.setLastLoginDate(new Date());
		member.setMemberRank(memberRankService.findDefault());
		Member spreadMember = memberService.findByUsername(spreadMemberUsername);
		if (spreadMember != null) {
			member.setParent(spreadMember);
		}
		memberService.save(member);
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		data.put("id",member.getId());
		data.put("token", JWTUtils.create(member.getId()+"",data));
		System.out.println("register:"+data.get("token"));
		return Result.success(data);
	}

}