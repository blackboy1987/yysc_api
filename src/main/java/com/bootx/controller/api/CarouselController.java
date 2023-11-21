
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiCarouselController")
@RequestMapping("/api/carousel")
public class CarouselController extends BaseController {

	/**
	 * 轮播
	 * @return
	 */
	@PostMapping
	public Result index(){
		return Result.success(jdbcTemplate.queryForList("select id,title2,title1,image,logo,downloadUrl from carousel;"));
	}
}