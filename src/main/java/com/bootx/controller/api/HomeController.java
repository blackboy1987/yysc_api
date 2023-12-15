
package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.dao.impl.CarouselDaoImpl;
import com.bootx.pojo.ToolBar;
import com.bootx.service.ActivityService;
import com.bootx.service.CarouselService;
import com.bootx.service.SoftService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author black
 */
@RestController("ApiHomeController")
@RequestMapping("/api/home")
public class HomeController extends BaseController {

	@Resource
	private CarouselService carouselService;

	@Resource
	private ActivityService activityService;

	@Resource
	private SoftService softService;

	@PostMapping
	public Result index(){
		Pageable pageable = new Pageable();
		Map<String,Object> data = new HashMap<>();
		// 轮播图
		data.put("carousels", carouselService.get());
		// 中间工具栏
		data.put("centerBars",homeCenterBar());
		// 活动
		data.put("activities",activityService.get());
		// 今日下载
		data.put("todayDownloadList",softService.get(pageable,"00",null));
		// 今日评论
		data.put("todayCommentList",softService.get(pageable,"01",null));
		// 随心看
		data.put("randomSeeList",softService.get(pageable,"2",null));
		System.out.println(JsonUtils.toJson(data));
		return Result.success(data);
	}



	public List<ToolBar> homeCenterBar() {
		/*try {
			String s = jdbcTemplate.queryForObject("select homeCenterBar from setting order by createdDate desc limit 1", String.class);
			return JsonUtils.toObject(s, new TypeReference<List<ToolBar>>() {
			});
		}catch (Exception e){
			return Collections.emptyList();
		}*/

		List<ToolBar> list = new ArrayList<>();
		ToolBar toolBar = new ToolBar();
		toolBar.setImage("https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/jK.png");
		toolBar.setName("热门");
		toolBar.setIsEnabled(true);
		list.add(toolBar);



		ToolBar toolBar1 = new ToolBar();
		toolBar1.setImage("https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/jp.png");
		toolBar1.setName("投稿");
		toolBar1.setIsEnabled(true);
		list.add(toolBar1);



		ToolBar toolBar2 = new ToolBar();
		toolBar2.setImage("https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/M_.png");
		toolBar2.setName("福利");
		toolBar2.setIsEnabled(true);
		list.add(toolBar2);



		ToolBar toolBar3 = new ToolBar();
		toolBar3.setImage("https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/Rv1.png");
		toolBar3.setName("群组");
		toolBar3.setIsEnabled(true);
		list.add(toolBar3);



		ToolBar toolBar4 = new ToolBar();
		toolBar4.setImage("https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/Nw1.png");
		toolBar4.setName("签到");
		toolBar4.setIsEnabled(true);
		list.add(toolBar4);

		return list;

	}

}