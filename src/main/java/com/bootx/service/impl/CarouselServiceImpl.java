package com.bootx.service.impl;

import com.bootx.entity.Carousel;
import com.bootx.service.CarouselService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class CarouselServiceImpl extends BaseServiceImpl<Carousel,Long> implements CarouselService{

    @Override
    public List<Map<String, Object>> get() {
        return jdbcTemplate.queryForList("select id,title2,title1,image,logo,downloadUrl from carousel;");
    }
}
