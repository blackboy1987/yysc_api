package com.bootx.service.impl;

import com.bootx.entity.Activity;
import com.bootx.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity,Long> implements ActivityService {

    @Override
    public List<Map<String, Object>> get() {
        return jdbcTemplate.queryForList("select image,title,url from activity order by orders asc");
    }
}
