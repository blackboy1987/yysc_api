
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
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
		data.put("id",member.getId());
		//
		if(StringUtils.isNotBlank(member.getAvatar())){
			data.put("avatar",member.getAvatar());
		}else{
			data.put("avatar","https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/avatar/"+(member.getId()%50)+".png");
		}

		data.put("point",member.getPoint());
		if(member.getMemberRank()!=null){
			data.put("nextPoint",member.getMemberRank().getPoint());
		}else{
			data.put("nextPoint",12345);
		}
		data.put("concernCount",jdbcTemplate.queryForObject("select count(id) from fan where member_id=?",Long.class,member.getId()));
		data.put("fanCount",jdbcTemplate.queryForObject("select count(id) from fan where fan_id=?",Long.class,member.getId()));
		return Result.success(data);
	}

	@PostMapping("/index")
	public Result index(@CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		return Result.success(data);
	}
}