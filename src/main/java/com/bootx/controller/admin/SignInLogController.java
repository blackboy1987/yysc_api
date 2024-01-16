package com.bootx.controller.admin;

import com.bootx.audit.Audit;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.HotSearch;
import com.bootx.entity.SignInLog;
import com.bootx.service.HotSearchService;
import com.bootx.service.MemberService;
import com.bootx.service.SignInLogService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("AdminSignInController")
@RequestMapping("/api/admin/signIn")
public class SignInLogController extends BaseController{

    @Resource
    private SignInLogService signInLogService;

    @Resource
    private MemberService memberService;


    /**
     * 列表
     */
    @PostMapping("/list")
    @Audit(action = "查询积分记录")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable,Long memberId) {
        if(memberId!=null){
            Page<SignInLog> page = signInLogService.findPage(pageable, memberService.find(memberId));
            return Result.success(page);
        }
        return Result.success(signInLogService.findPage(pageable));

    }

}
