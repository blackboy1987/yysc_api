
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("MemberRewardController")
@RequestMapping("/api/member/reward")
public class RewardController extends BaseController {

	@Resource
	private PointLogService pointLogService;
	@Resource
	private MemberService memberService;

	@PostMapping("/ad")
	public Result ad(HttpServletRequest request, @CurrentUser Member member) {
		PointLog pointLog = pointLogService.create(member,100L,"看广告",1);
		member.setPoint(member.getPoint()+pointLog.getCredit());
		member.setRemainPoint(member.getRemainPoint()+pointLog.getCredit());
		memberService.update(member);
		return Result.success();
	}

	@PostMapping("/loadAd")
	public Result loadAd(HttpServletRequest request, @CurrentUser Member member) {
		return Result.success(jdbcTemplate.queryForMap("select sum(credit) point,count(id) times from pointlog where member_id=? and type=1;",member.getId()));
	}

	@PostMapping("/rank")
	public Result rank(HttpServletRequest request, @CurrentUser Member member,Integer type) {
		return Result.success(jdbcTemplate.queryForList("select count(pointlog.id) times,member.username,member.avatar,memberrank.name rankName from pointlog,member,memberrank where member.id=pointlog.member_id and member.memberRank_id=memberrank.id and pointlog.type=? order by count(pointlog.id)",type));
	}
}