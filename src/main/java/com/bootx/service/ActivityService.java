package com.bootx.service;

import com.bootx.entity.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService extends BaseService<Activity,Long> {
    List<Map<String,Object>> get();
}
