package com.bootx.service.impl;

import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;
import com.bootx.service.SignInLogService;
import com.bootx.util.IPUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class SignInLogServiceImpl extends BaseServiceImpl<SignInLog,Long> implements SignInLogService {
    @Override
    public SignInLog create(Member member, HttpServletRequest request) {
        SignInLog signInLog = new SignInLog();
        signInLog.setIp(IPUtils.getIpAddr(request));
        signInLog.setMember(member);
        signInLog.setRewardPoints(5L);
        return super.save(signInLog);
    }
}
