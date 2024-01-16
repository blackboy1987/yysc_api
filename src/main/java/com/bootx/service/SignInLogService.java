package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author black
 */
public interface SignInLogService extends BaseService<SignInLog,Long>{
    SignInLog create(Member member, HttpServletRequest request);

    Page<SignInLog> findPage(Pageable pageable, Member member);
}
