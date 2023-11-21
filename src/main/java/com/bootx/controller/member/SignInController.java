
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.entity.SignInLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SignInLogService;
import com.bootx.util.IPUtils;
import com.bootx.util.JWTUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberSignInController")
@RequestMapping("/api/member/signIn")
public class SignInController {

	@Resource
	private SignInLogService signInLogService;
	@Resource
	private PointLogService pointLogService;
	@Resource
	private MemberService memberService;

	@PostMapping
	public Result index(HttpServletRequest request, @CurrentUser Member member) {
		SignInLog signInLog = signInLogService.create(member,request);
		PointLog pointLog = pointLogService.create(member,signInLog.getRewardPoints(),"签到奖励",0);
		member.setPoint(member.getPoint()+pointLog.getCredit());
		memberService.update(member);
		return Result.success();
	}
}