package com.bootx.service.impl;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;
import com.bootx.service.SoftImageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author black
 */
@Service
public class SoftImageServiceImpl extends BaseServiceImpl<SoftImage, Long> implements SoftImageService {
    @Override
    public void remove(Soft soft) {
        jdbcTemplate.update("delete from softimage where soft_id=?",soft.getId());
    }

    @Override
    public void create(Soft soft, List<String> images) {
        remove(soft);

        images.forEach(item->{
            if(StringUtils.isNoneBlank(item)){
                SoftImage softImage = new SoftImage();
                softImage.setSoft(soft);
                softImage.setUrl(item);
                softImage.setStatus(1);
                softImage.setType(1);
                super.save(softImage);
            }
        });


    }
}