package com.bootx.controller.member;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.util.DateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController("MemberMessageController")
@RequestMapping("/api/member/message")
public class MessageController {

    @PostMapping("/list")
    public Result list(Pageable pageable, @CurrentUser Member member) {
        return Result.success(Page.emptyPage(pageable));
    }
}
