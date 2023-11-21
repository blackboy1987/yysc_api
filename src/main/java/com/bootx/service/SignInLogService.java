package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;
import jakarta.servlet.http.HttpServletRequest;

public interface SignInLogService extends BaseService<SignInLog,Long>{
    SignInLog create(Member member, HttpServletRequest request);
}
