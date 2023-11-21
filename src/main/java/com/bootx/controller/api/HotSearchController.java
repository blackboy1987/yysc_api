
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiHotSearchController")
@RequestMapping("/api/hotSearch")
public class HotSearchController extends BaseController {

	@PostMapping
	public Result index(){
		return Result.success(jdbcTemplate.queryForList("select name from hotSearch order by orders asc;"));
	}
}