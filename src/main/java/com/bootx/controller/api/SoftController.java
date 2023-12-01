
package com.bootx.controller.api;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("ApiSoftController")
@RequestMapping("/api/soft")
public class SoftController extends BaseController {

	@Resource
	private SoftService softService;

	@PostMapping("/list")
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable,Long categoryId) {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select soft.id,soft.name,soft.logo from soft_categories,soft where softs_id=soft.id and categories_id=? limit ?,?;", categoryId, (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());

		return Result.success();
	}

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
		System.out.println(member);
		String fromSql = "from soft";
		if(categoryId!=null&&categoryId!=0){
			fromSql = "from soft_categories,soft where softs_id=soft.id and categories_id="+categoryId;
		}
		List<Map<String, Object>> maps;
		String pageQuery = "limit "+(pageable.getPageNumber()-1)*pageable.getPageSize()+","+ pageable.getPageSize();
		if(StringUtils.equalsIgnoreCase("00",orderBy)){
			maps = jdbcTemplate.queryForList("select size, score,versionName, id, downloads,logo,name "+fromSql+" order by downloads desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
				item.put("score",(item.get("score")+"").substring(0,3));
			});
		}else if(StringUtils.equalsIgnoreCase("01",orderBy)){
			maps = jdbcTemplate.queryForList("select id,reviewCount,score,logo,name,versionName,size "+fromSql+" order by score desc "+pageQuery);
			maps.forEach(item->{
				item.put("memo","今日"+(item.get("reviewCount")==null?0:item.get("reviewCount"))+"条评论");
				item.put("score",(item.get("score")+"").substring(0,3));
			});
		}else if(StringUtils.equalsIgnoreCase("2",orderBy)){
			maps = jdbcTemplate.queryForList("SELECT size, id,downloads,logo,name,score FROM soft WHERE id >= ((SELECT MAX(id) FROM soft)-(SELECT MIN(id) FROM soft)) * RAND() + (SELECT MIN(id) FROM soft) LIMIT 20");
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
				item.put("score",(item.get("score")+"").substring(0,3));
			});
		}else if(StringUtils.equalsIgnoreCase("7",orderBy)){
			maps = jdbcTemplate.queryForList("select size, id, downloads,logo,name,score,versionName "+fromSql+" order by downloadCount desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
				item.put("score",(item.get("score")+"").substring(0,3));
			});
		}else if(StringUtils.equalsIgnoreCase("8",orderBy)){
			maps = jdbcTemplate.queryForList("select size, id,logo,name "+fromSql+" order by downloads desc "+pageQuery);
		}else{
			maps = jdbcTemplate.queryForList("select size, id,downloads ,logo,name,score "+fromSql+" order by downloads desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
				item.put("score",(item.get("score")+"").substring(0,3));
			});
		}
		return Result.success(maps);
	}

	@PostMapping("/detail")
	public Result detail(Long id) {
		Map<String, Object> data = jdbcTemplate.queryForMap("select fullName, score,size,downloads,name,logo,updateDate from soft where id=?", id);
		try {
			data.putAll(jdbcTemplate.queryForMap("select introduce,memo,updatedContent from softinfo where soft_id=?",id));
		}catch (Exception e){
			data.put("introduce","");
			data.put("memo","");
			data.put("updatedContent","");
		}
		List<Map<String, Object>> images = jdbcTemplate.queryForList("select url from softimage where soft_id=? and status=1;", id);
		data.put("score",(data.get("score")+"").substring(0,3));

		Long downloads = Long.valueOf(data.get("downloads") + "");
		if(downloads>=10000){
			data.put("downloads",String.format("%.2f",downloads/10000.0)+"万");
		}else{
			data.put("downloads",downloads+"次下载");
		}

		List<String> imageList = new ArrayList<>();
		images.forEach(item->{
			imageList.add(item.get("url")+"");
		});
		data.put("images",imageList);
		return Result.success(data);
	}


	@PostMapping("/download")
	@Audit(action = "软件下载")
	public Result download(Long id) {
		try {
			Map<String,Object> downloadInfo = jdbcTemplate.queryForMap("select downloadUrl,name,versionName,size from soft where id=?", id);
			if(StringUtils.startsWith(downloadInfo.get("downloadUrl")+"","http")&&StringUtils.endsWith(downloadInfo.get("downloadUrl")+"",".apk")){
				// 下载次数加1
				softService.updateDownloads(id,1);
                downloadInfo.putIfAbsent("versionName", "1.0.0");
				return Result.success(downloadInfo);
			}
		}catch (Exception ignored){

		}
		return Result.error("暂无下载地址");
	}
}