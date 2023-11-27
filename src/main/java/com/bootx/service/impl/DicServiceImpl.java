package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.DicDao;
import com.bootx.entity.Dic;
import com.bootx.entity.DicCategory;
import com.bootx.service.DicService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class DicServiceImpl extends BaseServiceImpl<Dic,Long> implements DicService {

    @Resource
    private DicDao dicDao;

    @Override
    public Page<Dic> findPage(Pageable pageable, DicCategory dicCategory) {
        return dicDao.findPage(pageable,dicCategory);
    }
}
