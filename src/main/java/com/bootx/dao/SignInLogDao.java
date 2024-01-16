package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;

/**
 * @author black
 */
public interface SignInLogDao extends BaseDao<SignInLog,Long>{
    Page<SignInLog> findPage(Pageable pageable, Member member);
}
