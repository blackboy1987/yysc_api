
package com.bootx.service;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftInfo;

public interface SoftInfoService extends BaseService<SoftInfo, Long> {

    void remove(Soft soft);
}