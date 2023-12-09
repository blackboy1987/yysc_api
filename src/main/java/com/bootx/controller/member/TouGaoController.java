
package com.bootx.controller.member;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.entity.SignInLog;
import com.bootx.pojo.SoftPOJO;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SignInLogService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author black
 */
@RestController("MemberTouGaoController")
@RequestMapping("/api/member/touGao")
public class TouGaoController extends BaseController {

	@Resource
	private SignInLogService signInLogService;
	@Resource
	private PointLogService pointLogService;
	@Resource
	private MemberService memberService;

	@Resource
	private SoftService softService;

	/**
	 * 获取分类
	 * @param request
	 * @param member
	 * @return
	 */
	@PostMapping("/category")
	public Result index(HttpServletRequest request, @CurrentUser Member member) {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select parent.id,parent.name,(select count(child.id) from category child where parent_id=parent.id) childrenCount from category parent where parent.parent_id is null order by orders asc ;");
		maps.forEach(item->{
			if(!StringUtils.equalsIgnoreCase(item.get("childrenCount")+"","0")){
				item.put("children",jdbcTemplate.queryForList("select id,name from category where parent_id=? order by orders asc ",item.get("id")));
			}
			item.remove("childrenCount");
		});
		return Result.success(maps);
	}

	@PostMapping("/init")
	public Result init(HttpServletRequest request, @CurrentUser Member member) {
		// 渠道
		// 广告类型
		// 付费模式
		// 运营方式
		// 应用优点
		return Result.success();
	}

	@PostMapping("/save")
	public Result save(HttpServletRequest request, SoftPOJO softPOJO, @CurrentUser Member member) {
		softService.create(softPOJO);

		return Result.success(softPOJO);
	}
}