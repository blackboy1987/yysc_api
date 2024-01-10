
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

import java.util.*;
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
		return Result.success(jdbcTemplate.queryForList("select id,downloads,logo from soft order by downloads desc limit 50;"));
	}


	/**
	 * 广告
	 * @return
	 */
	@PostMapping("/adConfig")
	public Map<String,Object> adConfig(){
		try {

			return jdbcTemplate.queryForMap("select bannerAdId,feedAdId,fullScreenVideoAdId,interAdId,mediaId,rewardVideoAdId,splashAdId,templateAdId,videoFeedAdId from adconfig order by id desc limit 1");
		}catch (Exception e){
			return Collections.emptyMap();
		}
	}

	/**
	 * 广告
	 * @return
	 */
	@PostMapping("/appRank")
	public Result appRank(){
		List<Map<String,Object>> list = new ArrayList<>();

		Map<String,Object> map1 = new HashMap<>();
		map1.put("id",1);
		map1.put("title","今日应用榜");
		List<Map<String,Object>> children1 = new ArrayList<>();
		Map<String,Object> child11 = new HashMap<>();
		child11.put("id",1);
		child11.put("title","今日下载榜");
		children1.add(child11);
		Map<String,Object> child112 = new HashMap<>();
		child112.put("id",2);
		child112.put("title","今日投币榜");
		children1.add(child112);
		Map<String,Object> child113 = new HashMap<>();
		child113.put("id",2);
		child113.put("title","今日好评榜");
		children1.add(child112);
		map1.put("children",children1);
		list.add(map1);

		Map<String,Object> map2 = new HashMap<>();
		map2.put("id",2);
		map2.put("title","应用总榜");
		List<Map<String,Object>> children21 = new ArrayList<>();
		Map<String,Object> child21 = new HashMap<>();
		child21.put("id",1);
		child21.put("title","下载总榜");
		children21.add(child21);
		Map<String,Object> child122 = new HashMap<>();
		child122.put("id",2);
		child122.put("title","投币总榜");
		children21.add(child122);
		Map<String,Object> child123 = new HashMap<>();
		child123.put("id",2);
		child123.put("title","最多评论");
		children21.add(child123);
		map2.put("children",children21);
		list.add(map2);

		Map<String,Object> map3 = new HashMap<>();
		map3.put("id",3);
		map3.put("title","封神榜");
		List<Map<String,Object>> children31 = new ArrayList<>();
		Map<String,Object> child31 = new HashMap<>();
		child31.put("id",1);
		child31.put("title","投稿大师");
		children31.add(child31);
		Map<String,Object> child132 = new HashMap<>();
		child132.put("id",2);
		child132.put("title","全站富豪");
		children31.add(child132);
		Map<String,Object> child133 = new HashMap<>();
		child133.put("id",3);
		child133.put("title","诸神黄昏");
		children31.add(child133);
		Map<String,Object> child134 = new HashMap<>();
		child134.put("id",4);
		child134.put("title","激励大帝");
		children31.add(child134);
		map3.put("children",children31);
		list.add(map3);

		Map<String,Object> map4 = new HashMap<>();
		map4.put("id",4);
		map4.put("title","达人榜");
		List<Map<String,Object>> children41 = new ArrayList<>();
		Map<String,Object> child41 = new HashMap<>();
		child41.put("id",1);
		child41.put("title","邀请达人");
		children41.add(child41);
		Map<String,Object> child142 = new HashMap<>();
		child142.put("id",2);
		child142.put("title","签到达人");
		children41.add(child142);
		Map<String,Object> child143 = new HashMap<>();
		child143.put("id",3);
		child143.put("title","名门望族");
		children41.add(child143);
		Map<String,Object> child144 = new HashMap<>();
		child144.put("id",4);
		child144.put("title","检查官");
		children41.add(child144);
		Map<String,Object> child145 = new HashMap<>();
		child145.put("id",5);
		child145.put("title","称号狂魔");
		children41.add(child145);
		map4.put("children",children41);
		list.add(map4);

		return Result.success(list);
	}


}