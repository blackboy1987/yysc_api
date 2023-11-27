
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
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				item.put("children",jdbcTemplate.queryForList("select id,name from category where parent_id=? order by orders asc "));
			}
			item.remove("childrenCount");
		});
		return Result.success(maps);
	}

	@PostMapping("/quDao")
	public Result quDao(HttpServletRequest request, @CurrentUser Member member) {
		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String,Object> map =new HashMap<>();
		map.put("key",1);
		map.put("name","官方版");
		maps.add(map);
		Map<String,Object> map1 =new HashMap<>();
		map1.put("key",2);
		map1.put("name","国际版");
		maps.add(map1);

		Map<String,Object> map2 =new HashMap<>();
		map2.put("key",3);
		map2.put("name","测试版");
		maps.add(map2);

		Map<String,Object> map3 =new HashMap<>();
		map3.put("key",3);
		map3.put("name","汉化版");
		maps.add(map3);

		return Result.success(maps);
	}
}