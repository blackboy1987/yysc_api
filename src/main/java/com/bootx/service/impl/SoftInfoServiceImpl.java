package com.bootx.service.impl;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftInfo;
import com.bootx.service.SoftInfoService;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class SoftInfoServiceImpl extends BaseServiceImpl<SoftInfo, Long> implements SoftInfoService {
    @Override
    public void remove(Soft soft) {
        jdbcTemplate.update("delete from softinfo where soft_id=?",soft.getId());
    }

    @Override
    public void create(Soft soft, String html) {
        remove(soft);
        SoftInfo softInfo = new SoftInfo();
        softInfo.setSoft(soft);
        softInfo.setIntroduce(html);
        super.save(softInfo);
    }
}