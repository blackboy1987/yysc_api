
package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.MemberService;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author black
 */
@RestController("ApiSearchController")
@RequestMapping("/api/search")
public class SearchController extends BaseController {

	@Resource
	private MemberService memberService;
	@Resource
	private SoftService softService;

	@PostMapping("/type")
	@JsonView(BaseEntity.PageView.class)
	public Result type() {
		List<String> type = new ArrayList<>();
		type.add("应用");
		//type.add("全网");
		//type.add("广场");
		type.add("用户");
		return Result.success(type);
	}
	@PostMapping
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable,String keywords,Integer type) {
		System.out.println(pageable.getPageNumber()+":"+type);
		if(type==0){
			// 站内
			return Result.success(softService.search(keywords,pageable));
		} else if (type==1) {
			// 全网
			return Result.success(memberService.search(keywords,pageable));
		} else if (type==2) {
			// 广场
		} else if (type==3) {
			// 用户
			return Result.success(memberService.search(keywords,pageable));
		}
		return Result.success(Collections.emptyMap());
	}
}