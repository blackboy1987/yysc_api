package com.bootx.service.impl;

import com.bootx.entity.Fan;
import com.bootx.entity.Member;
import com.bootx.service.FanService;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class FanServiceImpl extends BaseServiceImpl<Fan,Long> implements FanService {

    @Override
    public void create(Member member, Member fan) {
        Fan fan1 = new Fan();
        fan1.setFan(fan);
        fan1.setMember(member);
        super.save(fan1);
    }
}
