
package com.bootx.controller.member;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.util.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberFanController")
@RequestMapping("/api/member/fan")
public class FanController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/list")
	public Result list(Pageable pageable, @CurrentUser Member member,Integer type) {
		List<Map<String, Object>> maps = new ArrayList<>();
		if(type==0){
			maps = jdbcTemplate.queryForList("select member.id,member.username,member.avatar,member.remainPoint from fan,member where member.id=fan.fan_id and member_id=? order by fan.createdDate desc limit ?,?;", member.getId(), (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
		}else{
			maps = jdbcTemplate.queryForList("select member.id,member.username,member.avatar,member.remainPoint from fan,member where member.id=fan.fan_id and fan_id=? order by fan.createdDate desc limit ?,?;", member.getId(), (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
		}
		for (Map<String, Object> map : maps) {
            map.computeIfAbsent("avatar", k -> "https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/avatar/" + (Long.valueOf(map.get("id")+"") % 50) + ".png");
		}

		return Result.success(maps);
	}

	/**
	 * 取关
	 * @param fanId
	 * @param member
	 * @return
	 */
	@PostMapping("/delete")
	public Result delete(Long fanId, @CurrentUser Member member,Integer type) {
		if(type==0){
			jdbcTemplate.update("delete from fan where fan_id=? and member_id=?",fanId,member.getId());
		}else{
			jdbcTemplate.update("delete from fan where fan_id=? and member_id=?",member.getId(),fanId);
		}
		return Result.success();
	}
}