package com.bootx.service;

import com.bootx.entity.Carousel;

import java.util.List;
import java.util.Map;

public interface CarouselService extends BaseService<Carousel,Long> {
    List<Map<String, Object>> get();
}
