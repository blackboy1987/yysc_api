package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Admin;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Carousel;
import com.bootx.service.CarouselService;
import com.bootx.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminMemberController")
@RequestMapping("/api/admin/member")
public class MemberController extends BaseController{

    @Resource
    private MemberService memberService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "会员查询")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable) {
        return Result.success(memberService.findPage(pageable));
    }

}
