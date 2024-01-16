package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.SignInLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;
import com.bootx.service.SignInLogService;
import com.bootx.util.IPUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class SignInLogServiceImpl extends BaseServiceImpl<SignInLog,Long> implements SignInLogService {

    @Resource
    private SignInLogDao signInLogDao;

    @Override
    public SignInLog create(Member member, HttpServletRequest request) {
        SignInLog signInLog = new SignInLog();
        signInLog.setIp(IPUtils.getIpAddr(request));
        signInLog.setMember(member);
        signInLog.setRewardPoints(5L);
        return super.save(signInLog);
    }

    @Override
    public Page<SignInLog> findPage(Pageable pageable, Member member) {
        return signInLogDao.findPage(pageable,member);
    }
}
