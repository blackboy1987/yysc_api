package com.bootx.service.impl;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftInfo;
import com.bootx.service.SoftInfoService;
import org.springframework.stereotype.Service;

@Service
public class SoftInfoServiceImpl extends BaseServiceImpl<SoftInfo, Long> implements SoftInfoService {
    @Override
    public void remove(Soft soft) {
        jdbcTemplate.update("delete from softinfo where soft_id=?",soft.getId());
    }
}