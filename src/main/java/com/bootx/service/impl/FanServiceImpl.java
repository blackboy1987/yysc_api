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
        if(member==null || fan == null){
            return ;
        }
        Long l = jdbcTemplate.queryForObject("select count(fan.id) from fan where member_id=? and fan_id=?", Long.class, member.getId(), fan.getId());
        if(l==null || l==0){
            Fan fan1 = new Fan();
            fan1.setFan(fan);
            fan1.setMember(member);
            super.save(fan1);
        }
    }
}
