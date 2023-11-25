package com.bootx.service.impl;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.service.SoftImageService;
import org.springframework.stereotype.Service;

@Service
public class SoftImageServiceImpl extends BaseServiceImpl<SoftImage, Long> implements SoftImageService {
    @Override
    public void remove(Soft soft) {
        jdbcTemplate.update("delete from softimage where soft_id=?",soft.getId());
    }
}