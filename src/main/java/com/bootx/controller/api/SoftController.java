
package com.bootx.controller.api;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.security.CurrentUser;
import com.bootx.service.RedisService;
import com.bootx.service.SoftService;
import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author black
 */
@RestController("ApiSoftController")
@RequestMapping("/api/soft")
public class SoftController extends BaseController {

	@Resource
	private RedisService redisService;

	@Resource
	private SoftService softService;

	/**
	 * orderBy
	 *  0开头：今日
	 *  	00： 今日下载排行榜
	 *  	01： 今日好评排行榜
	 *  1：新鲜
	 *  2：随心看
	 *  3：最新发布排行
	 *  4：最近活跃排行
	 *  5：最多评价排行
	 *  6：最多投币排行
	 *  7：下载量排行
	 *  8: 热搜应用
	 * @return
	 */
	@PostMapping("/orderBy")
	@Audit(action = "软件列表")
	public Result orderBy(Pageable pageable, String orderBy, Long categoryId, @CurrentUser Member member){
		String cacheKey = orderBy+"_"+categoryId+"_"+pageable.getPageNumber()+"_"+pageable.getPageSize();
		List<Map<String, Object>> maps;
		try {
			maps = JsonUtils.toObject(redisService.get(cacheKey), new TypeReference<List<Map<String, Object>>>() {
			});
		}catch (Exception e){
			maps = softService.get(pageable,orderBy,categoryId);
			if(!maps.isEmpty()){
				redisService.set(cacheKey,JsonUtils.toJson(maps));
			}
		}

		return Result.success(maps);
	}




	@PostMapping("/more")
	@Audit(action = "更多信息")
	public Result more(Long id) {
		try {
			return Result.success(jdbcTemplate.queryForMap("select size,packageName,minSdkVersion,targetSdkVersion,versionName,versionCode from soft where id=?", id));
		}catch (Exception ignored){

		}
		return Result.success(Collections.emptyMap());
	}

	@PostMapping("/download")
	@Audit(action = "软件下载")
	@JsonView(Soft.DownloadView.class)
	public Result download(Long id) {
		Soft soft = softService.find(id);
		if(soft == null){
			return Result.error("软件不存在");
		}
		if(StringUtils.isBlank(soft.getVersionName())){
			soft.setVersionName("未知");
		}
		if(StringUtils.isBlank(soft.getDownloadUrl())){
			return Result.error("暂无下载地址");
		}
		if(!StringUtils.startsWith(soft.getDownloadUrl(),"http")){
			return Result.error("暂无下载地址");
		}
		if(!StringUtils.endsWith(soft.getDownloadUrl(),"apk")){
			return Result.error("暂无下载地址");
		}
		if(StringUtils.isBlank(soft.getVersionName())){
			soft.setVersionName("未知");
		}
		softService.updateDownloads(id,1);

		return Result.success(soft);
	}

	@PostMapping("/list")
	public Result list(Pageable pageable,Long categoryId) {
		return Result.success(softService.list(pageable,categoryId));
	}

	@PostMapping("/detail")
	public Result detail(Long id) {

		return Result.success(softService.detail(id));
	}
}