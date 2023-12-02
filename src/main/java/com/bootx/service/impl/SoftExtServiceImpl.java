package com.bootx.service.impl;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftExt;
import com.bootx.service.SoftExtService;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class SoftExtServiceImpl extends BaseServiceImpl<SoftExt, Long> implements SoftExtService {
    @Override
    public void remove(Soft soft) {
        jdbcTemplate.update("delete from SoftExt where soft_id=?",soft.getId());
    }

    @Override
    public void create(SoftExt softExt) {
        remove(softExt.getSoft());
        super.save(softExt);
    }
}