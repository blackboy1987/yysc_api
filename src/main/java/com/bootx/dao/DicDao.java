package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Dic;
import com.bootx.entity.DicCategory;

public interface DicDao extends BaseDao<Dic,Long>{
    Page<Dic> findPage(Pageable pageable, DicCategory dicCategory);
}
