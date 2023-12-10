
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftIconLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SoftIconLogService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("MemberSoftIconLogController")
@RequestMapping("/api/member/softIconLog")
public class SoftIconLogController extends BaseController {

	@Resource
	private SoftIconLogService softIconLogService;
	@Resource
	private PointLogService pointLogService;
	@Resource
	private MemberService memberService;
	@Resource
	private SoftService softService;

	/**
	 * 投币
	 * @param request
	 * @param member
	 * @param softIconLog
	 * @param softId
	 * @return
	 */
	@PostMapping("/reward")
	public Result reward(HttpServletRequest request, @CurrentUser Member member, SoftIconLog softIconLog,Long softId) {
		Soft soft = softService.find(softId);
		member.setRemainPoint(member.getRemainPoint()-softIconLog.getPoint());
		if(member.getRemainPoint()<0){
			return Result.error("您没有足够的硬币哦！");
		}
		memberService.update(member);
		/**
		 * 写入消费记录
		 */
		pointLogService.create(member,softIconLog.getPoint()*-1,"投币",2);
		softIconLog.setSoft(soft);
		softIconLog.setMember(member);
		softIconLogService.save(softIconLog);

		soft.setDonationIcon(soft.getDonationIcon()+softIconLog.getPoint());
		soft.setDonationMember(soft.getDonationMember()+1);
		softService.update(soft);

		return Result.success();
	}
}