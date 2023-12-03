
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Complaint;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.entity.SignInLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import com.bootx.util.DateUtils;
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
@RestController("MemberComplaintController")
@RequestMapping("/api/member/complaint")
public class ComplaintController extends BaseController {

	@Resource
	private ComplaintService complaintService;
	@Resource
	private MemberService memberService;

	@Resource
	private SoftService softService;

	@PostMapping("/save")
	public Result save(HttpServletRequest request, @CurrentUser Member member,Long softId, Complaint complaint) {
		complaint.setMember(member);
		complaint.setSoft(softService.find(softId));
		complaintService.save(complaint);
		return Result.success();
	}
}