
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.pojo.ToolBar;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author black
 */
@RestController("ApiIndexController")
@RequestMapping("/api")
public class IndexController extends BaseController {

	/**
	 * 热门下载排行榜
	 * @return
	 */
	@PostMapping("/list0")
	public Result list0(){
		return Result.success(jdbcTemplate.queryForList("select id,downloads,logo from soft order by downloads desc limit 50;"));
	}

	/**
	 * 好评
	 * @return
	 */
	@PostMapping("/list1")
	public Result list1(){
		return Result.success(jdbcTemplate.queryForList("select id,score,logo from soft order by score desc limit 30;"));
	}

	/**
	 * 最新活动
	 * @return
	 */
	@PostMapping("/active")
	public Result active(){
		return Result.success(jdbcTemplate.queryForList("select id,score,logo from soft order by score desc limit 30;"));
	}

	/**
	 * 今日下载排行榜
	 * @return
	 */
	@PostMapping("/list2")
	public Result list2(){
		return Result.success(jdbcTemplate.queryForList("select id,todayDownloads downloads,logo,name,score from soft order by todayDownloads desc limit 50;"));
	}

	/**
	 * 随便看看
	 * @return
	 */
	@PostMapping("/list3")
	public Result list3(){
		return Result.success(jdbcTemplate.queryForList("select id,downloads,logo from soft order by downloadCount desc limit 50;"));
	}

	@PostMapping("/homeCenterBar")
	public Result homeCenterBar(HttpServletRequest request) {
		try {
			String s = jdbcTemplate.queryForObject("select homeCenterBar from setting order by createdDate desc limit 1", String.class);
			List<ToolBar> list = JsonUtils.toObject(s, new TypeReference<List<ToolBar>>() {
			});
			return Result.success(list.stream().filter(ToolBar::getIsEnabled).collect(Collectors.toList()));
		}catch (Exception e){
			return Result.success(Collections.emptyList());
		}
	}

	@PostMapping("/activity")
	public Result active(HttpServletRequest request) {
		return Result.success(jdbcTemplate.queryForList("select image,title,url from activity order by orders asc"));
	}
}