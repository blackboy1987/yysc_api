
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
		Boolean isSign = isSign(request, member);
		if(!isSign){
			SignInLog signInLog = signInLogService.create(member,request);
			PointLog pointLog = pointLogService.create(member,signInLog.getRewardPoints(),"签到奖励",0);
			member.setPoint(member.getPoint()+pointLog.getCredit());
			member.setRemainPoint(member.getRemainPoint()+pointLog.getCredit());
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
		}
		// 连续签到天数
		data.put("isSign",isSign);
		data.put("days",member.getContinuousSignInDays());
		// 签到排名
		data.put("rank",jdbcTemplate.queryForObject("select count(id) from signinlog where createdDate>=? and createdDate<?;",Integer.class,DateUtils.formatDateToString(new Date(),"yyyy-MM-dd 00:00:00"),DateUtils.formatDateToString(member.getSignInDate(),"yyyy-MM-dd H:mm:ss")));
		data.put("list",jdbcTemplate.queryForList("select member.avatar,member.username,member.continuousSignInDays,DATE_FORMAT(signinlog.createdDate,'%Y-%m-%d %H:%i:%s') signDate from signinlog,member where member.id=signinlog.member_id and signinlog.createdDate>=? order by signinlog.createdDate desc",DateUtils.formatDateToString(new Date(),"yyyy-MM-dd 00:00:00")));
		return Result.success(data);
	}

	/**
	 * 判断是否签到
	 * @param request
	 * @param member
	 * @return
	 */
	@PostMapping("/isSign")
	public Boolean isSign(HttpServletRequest request, @CurrentUser Member member) {
		Integer count = jdbcTemplate.queryForObject("select count(id) from signinlog where member_id=? and createdDate>=? and createdDate<?;",Integer.class,member.getId(),DateUtils.formatDateToString(new Date(),"yyyy-MM-dd 00:00:00"),DateUtils.formatDateToString(new Date(),"yyyy-MM-dd H:mm:ss"));
		return count!=null&&count>0;
	}
}