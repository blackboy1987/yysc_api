
package com.bootx.controller.member;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
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
import com.bootx.util.DateUtils;
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
		softService.create(softPOJO,member);

		return Result.success(softPOJO);
	}

	@PostMapping("/list")
	@Audit(action = "上传应用列表")
	public Result list(HttpServletRequest request, Integer type, @CurrentUser Member member, Pageable pageable) {
		if(type==null){
			type = -1;
		}else if(type==0){
			type = -1;
		}else if(type==1){
			type = 1;
		}else if(type==2){
			type = 0;
		}else if(type==3){
			type = 2;
		}else if(type==4){
			type = 100;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select id,logo,name,versionName,DATE_FORMAT(createdDate,'%Y-%m-%d %H:%i:%s') createdDate from soft");
		List<Object> args = new ArrayList<>();
		sb.append(" where member_id=?");
		args.add(member.getId());
		if(type>0){
			sb.append(" and status=?");
			args.add(type);
		}
		sb.append(" order by createdDate desc");
		sb.append(" limit ?,?");
		args.add((pageable.getPageNumber()-1)*pageable.getPageSize());
		args.add(pageable.getPageSize());
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sb.toString(), args.toArray());
		maps.forEach(item->{
			item.put("createdDate",DateUtils.formatDateInfo(DateUtils.formatStringToDate(item.get("createdDate")+"","yyyy-MM-dd HH:mm:ss")));
		});
		return Result.success(maps);
	}
}