
package com.bootx.controller.admin;

import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.Setting;
import com.bootx.service.AdminService;
import com.bootx.service.SettingService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController
@RequestMapping("/api/admin")
public class IndexController extends BaseController {

	@Resource
	private AdminService adminService;

	@Resource
	private SettingService settingService;

	@PostMapping("/currentUser")
	public Result currentUser(HttpServletRequest request) {
		String token = request.getHeader("token");
		Admin admin = adminService.getCurrent();
		Map<String,Object> data = new HashMap<>();
		data.put("username",admin.getUsername());
		return Result.success(data);
	}


	@PostMapping("/homeCenterBar")
	public Result homeCenterBar(HttpServletRequest request) {
		try {
			String s = jdbcTemplate.queryForObject("select homeCenterBar from setting order by createdDate desc limit 1", String.class);
			return Result.success(JsonUtils.toObject(s, new TypeReference<List<Map<String,Object>>>() {
			}));
		}catch (Exception e){
			return Result.success(Collections.emptyList());
		}
	}


	@PostMapping("/homeCenterBarSave")
	public Result homeCenterBarSave(HttpServletRequest request,String str) {
		Long id = 0L;
		try {
			id = jdbcTemplate.queryForObject("select id from setting order by createdDate desc limit 1", Long.class);

		}catch (Exception ignored){

		}
		Setting setting = settingService.find(id);
		if(setting==null){
			setting = new Setting();
			setting.setHomeCenterBar(str);
			settingService.save(setting);
		}else{
			setting.setHomeCenterBar(str);
			settingService.update(setting);
		}
		return homeCenterBar(request);
	}


	@PostMapping("/homeBottomBar")
	public Result homeBottomBar(HttpServletRequest request) {
		try {
			String s = jdbcTemplate.queryForObject("select homeBottomBar from setting order by createdDate desc limit 1", String.class);
			return Result.success(JsonUtils.toObject(s, new TypeReference<List<Map<String,Object>>>() {
			}));
		}catch (Exception e){
			return Result.success(Collections.emptyList());
		}
	}


	@PostMapping("/homeBottomBarSave")
	public Result homeBottomBarSave(HttpServletRequest request,String str) {
		Long id = 0L;
		try {
			id = jdbcTemplate.queryForObject("select id from setting order by createdDate desc limit 1", Long.class);

		}catch (Exception ignored){

		}
		Setting setting = settingService.find(id);
		if(setting==null){
			setting = new Setting();
			setting.setHomeBottomBar(str);
			settingService.save(setting);
		}else{
			setting.setHomeBottomBar(str);
			settingService.update(setting);
		}
		return homeBottomBar(request);
	}
}