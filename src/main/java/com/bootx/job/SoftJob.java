package com.bootx.job;

import com.bootx.entity.Member;
import com.bootx.entity.PointLog;
import com.bootx.entity.Soft;
import com.bootx.service.MemberService;
import com.bootx.service.PointLogService;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SoftJob {

    @Resource
    private PointLogService pointLogService;

    @Resource
    private MemberService memberService;

    @Resource
    private JdbcTemplate jdbcTemplate;
}
