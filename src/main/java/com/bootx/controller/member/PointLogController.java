
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

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberPointLogController")
@RequestMapping("/api/member/pointLog")
public class PointLogController {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@PostMapping("/list")
	public Result list(Pageable pageable, @CurrentUser Member member) {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id,memo,credit point,NOW()-createdDate seconds from pointlog where member_id=? order by createdDate desc limit ?,?;", 1, (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());

		for (Map<String, Object> map : maps) {
			Integer seconds = Integer.valueOf(map.get("seconds")+"");
			map.put("seconds", DateUtils.getDuration(seconds));
		}

		return Result.success(maps);
	}
}