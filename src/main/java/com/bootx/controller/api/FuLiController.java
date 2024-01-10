
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.FuLi;
import com.bootx.service.FuLiService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiFuLiController")
@RequestMapping("/api/fuLi")
public class FuLiController extends BaseController {

	@Resource
	private FuLiService fuLiService;

	@PostMapping
	@JsonView(FuLi.ListView.class)
	public Result index(){
		return Result.success(fuLiService.findAll());
	}


}