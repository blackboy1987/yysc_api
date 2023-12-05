
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
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
@RestController("ApiSettingController")
@RequestMapping("/api/setting")
public class SettingController extends BaseController {

	@PostMapping
	public Result index(Long id) {
		Map<String,Object> data = new HashMap<>();
		List<Map<String,Object>> userMenus = initUserMenus();

		data.put("userMenus",userMenus);
		return Result.success(data);
	}
	private List<Map<String, Object>> initUserMenus() {
		List<Map<String,Object>> userMenus = new ArrayList<>();
		userMenus.add(initUserMenu("应用投稿","Rv1","TouGaoFrame"));
		userMenus.add(initUserMenu("应用更新","jp","AppUpdateFrame"));
		userMenus.add(initUserMenu("我的收藏","jK","CollectFrame"));
		userMenus.add(initUserMenu("签到","Yh","SignInFrame"));
		userMenus.add(initUserMenu("下载管理","M_","DownloadManagerFrame"));
		userMenus.add(initUserMenu("支持一下","Nw1","SupportFrame"));
		userMenus.add(initUserMenu("排行榜","BG","SupportRankFrame"));
		userMenus.add(initUserMenu("邀请福利","iN","FuLiFrame"));
		userMenus.add(initUserMenu("我的讨论","s4",""));
		userMenus.add(initUserMenu("我的帖子","-Z",""));
		userMenus.add(initUserMenu("我的关注","yQ",""));
		userMenus.add(initUserMenu("我的评分","jZ",""));
		userMenus.add(initUserMenu("我的称号","7E",""));
		userMenus.add(initUserMenu("精选风格","iP",""));
		userMenus.add(initUserMenu("主题风格","6V",""));
		userMenus.add(initUserMenu("交流群组","1b",""));

		return userMenus;
	}
	private Map<String, Object> initUserMenu(String title,String icon,String url) {
		Map<String,Object> map = new HashMap<>();
		map.put("icon","http://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/yysc/res/"+icon+".png");
		map.put("title",title);
		map.put("url",url);
		return map;
	}
}