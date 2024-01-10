
package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
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
@RestController("adminQunZuController")
@RequestMapping("/api/admin/qunZu")
public class QunZuController extends BaseController {

	@Resource
	private QunZuService qunZuService;


	/**
	 * 保存
	 */
	@PostMapping("/save")
	public Result save(QunZu fuLi) {
		qunZuService.save(fuLi);
		return Result.success();
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public Result update(QunZu fuLi) {
		qunZuService.update(fuLi);
		return Result.success();
	}

	/**
	 * 列表
	 */
	@PostMapping("/list")
	@Audit(action = "项目查询")
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable) {
		return Result.success(qunZuService.findPage(pageable));
	}

	/**
	 * 删除
	 */
	@PostMapping("/delete")
	public Result delete(Long[] ids) {
		qunZuService.delete(ids);
		return Result.success();
	}
}