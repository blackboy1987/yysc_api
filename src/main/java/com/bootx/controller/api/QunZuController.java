
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.QunZu;
import com.bootx.service.QunZuService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiQunZuController")
@RequestMapping("/api/qunZu")
public class QunZuController extends BaseController {

	@Resource
	private QunZuService qunZuService;

	@PostMapping
	@JsonView(QunZu.ListView.class)
	public Result index(){
		return Result.success(qunZuService.findAll());
	}


}