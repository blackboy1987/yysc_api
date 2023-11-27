package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Dic;
import com.bootx.entity.DicCategory;

public interface DicService extends BaseService<Dic,Long> {
    Page<Dic> findPage(Pageable pageable, DicCategory dicCategory);
}
