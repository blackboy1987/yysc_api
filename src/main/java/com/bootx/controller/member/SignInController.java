
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.entity.SignInLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SignInLogService;
import com.bootx.util.DateUtils;
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
public class SignInController extends BaseController {

	@Resource
	private SignInLogService signInLogService;
	@Resource
	private PointLogService pointLogService;
	@Resource
	private MemberService memberService;

	@PostMapping
	public Result index(HttpServletRequest request, @CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("continuousSignInDays",0);
		data.put("rank",0);
		member = memberService.find(1L);
		Boolean isSign = isSign(request, member);
		if(!isSign){
			SignInLog signInLog = signInLogService.create(member,request);
			PointLog pointLog = pointLogService.create(member,signInLog.getRewardPoints(),"签到奖励",0);
			member.setPoint(member.getPoint()+pointLog.getCredit());
			// 调整累计签到和连续签到数据
			member.setSignInDays(member.getSignInDays()+1);
			// 检测昨日有没有签到
			Date yesterday = DateUtils.getNextDay(-1);
			Integer l = jdbcTemplate.queryForObject("select count(id) from signinlog where createdDate>=? and createdDate<=? and member_id=?;", Integer.class, DateUtils.formatDateToString(yesterday, "yyyy-MM-dd 00:00:00"), DateUtils.formatDateToString(yesterday, "yyyy-MM-dd 23:59:59"), 1L);
			if(l==null || l==0){
				member.setContinuousSignInDays(1);
			}else{
				member.setContinuousSignInDays(member.getContinuousSignInDays()+1);
			}
			member.setSignInDate(new Date());
			memberService.update(member);
			data.put("days",member.getContinuousSignInDays());
			data.put("rank",jdbcTemplate.queryForObject("select count(id) from signinlog where createdDate>=? and createdDate<?;",Integer.class,DateUtils.formatDateToString(new Date(),"yyyy-MM-dd 00:00:00"),DateUtils.formatDateToString(member.getSignInDate(),"yyyy-MM-dd H:mm:ss")));
		}
		return Result.success();
	}

	@PostMapping("/isSign")
	public Boolean isSign(HttpServletRequest request, @CurrentUser Member member) {
		Integer count = jdbcTemplate.queryForObject("select count(id) from signinlog where member_id=? and createdDate>=? and createdDate<?;",Integer.class,member.getId(),DateUtils.formatDateToString(new Date(),"yyyy-MM-dd 00:00:00"),DateUtils.formatDateToString(new Date(),"yyyy-MM-dd H:mm:ss"));
		return count!=null&&count>0;
	}
}