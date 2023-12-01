
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.SoftIconLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SoftIconLogService;
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
	private PointLogService pointLogService;

	@PostMapping("/reward")
	public Result reward(HttpServletRequest request, @CurrentUser Member member, SoftIconLog softIconLog) {

		member.setPoint(member.getPoint()-softIconLog.getPoint());
		if(member.getPoint()<0){
			return Result.error("您没有足够的硬币哦！");
		}
		pointLogService.create(member,)



		memberService.update(member);




		return Result.success();
	}
}