
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
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
	public Result currentUser(@CurrentUser Member member) {
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
			data.put("nextPoint",jdbcTemplate.queryForObject("select point from memberrank where id>? order by id asc limit 1;",Long.class,member.getMemberRank().getId()));
		}else{
			data.put("nextPoint",0);
		}
		data.put("concernCount",jdbcTemplate.queryForObject("select count(id) from fan where member_id=?",Long.class,member.getId()));
		data.put("fanCount",jdbcTemplate.queryForObject("select count(id) from fan where fan_id=?",Long.class,member.getId()));
		return Result.success(data);
	}

	@PostMapping("/update")
	public Result update(String username,@CurrentUser Member member) {
		/*if(memberService.usernameUnique(member.getId(),username)){
			member.setUsername(username);
			memberService.update(member);
			return Result.success();
		}
		return Result.error("昵称已存在");*/
		return Result.error("暂不支持修改昵称");
	}

	@PostMapping("/index")
	public Result index(@CurrentUser Member member) {
		Map<String,Object> data = new HashMap<>();
		data.put("username",member.getUsername());
		return Result.success(data);
	}
}