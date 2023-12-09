package com.bootx.service;

import com.bootx.entity.Fan;
import com.bootx.entity.Member;

/**
 * @author black
 */
public interface FanService extends BaseService<Fan,Long> {
    void create(Member member, Member fan);
}
