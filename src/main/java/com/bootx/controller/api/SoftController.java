
package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
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
	public Result orderBy(Pageable pageable,String orderBy,Long categoryId){
		String fromSql = "from soft";
		if(categoryId!=null&&categoryId!=0){
			fromSql = "from soft_categories,soft where softs_id=soft.id and categories_id="+categoryId;
		}
		List<Map<String, Object>> maps;
		String pageQuery = "limit "+(pageable.getPageNumber()-1)*pageable.getPageSize()+","+ pageable.getPageSize();
		if(StringUtils.equalsIgnoreCase("00",orderBy)){
			maps = jdbcTemplate.queryForList("select id,downloadCount downloads,logo,name "+fromSql+" order by downloadCount desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
			});
		}else if(StringUtils.equalsIgnoreCase("01",orderBy)){
			maps = jdbcTemplate.queryForList("select id,reviewCount,score,logo,name "+fromSql+" order by score desc "+pageQuery);
			maps.forEach(item->{
				item.put("memo","今日"+item.get("reviewCount")+"条评论");
			});
		}else if(StringUtils.equalsIgnoreCase("2",orderBy)){
			maps = jdbcTemplate.queryForList("SELECT id,downloads,logo,name,score FROM soft WHERE id >= ((SELECT MAX(id) FROM soft)-(SELECT MIN(id) FROM soft)) * RAND() + (SELECT MIN(id) FROM soft) LIMIT 20");
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{

					item.put("memo",downloads+"次下载");
				}
			});
		}else if(StringUtils.equalsIgnoreCase("7",orderBy)){
			maps = jdbcTemplate.queryForList("select id, downloads,logo,name "+fromSql+" order by downloadCount desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{

					item.put("memo",downloads+"次下载");
				}
			});
		}else if(StringUtils.equalsIgnoreCase("8",orderBy)){
			maps = jdbcTemplate.queryForList("select id,logo,name "+fromSql+" order by downloadCount desc "+pageQuery);
		}else{
			maps = jdbcTemplate.queryForList("select id,downloadCount downloads,logo,name,score "+fromSql+" order by downloadCount desc "+pageQuery);
			maps.forEach(item->{
				Long downloads = Long.valueOf(item.get("downloads") + "");
				if(downloads>=10000){
					item.put("memo",String.format("%.2f",downloads/10000.0)+"万次下载");
				}else{
					item.put("memo",downloads+"次下载");
				}
			});
		}



		return Result.success(maps);
	}

}