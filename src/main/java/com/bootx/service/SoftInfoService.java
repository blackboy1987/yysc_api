
package com.bootx.service;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftInfo;

/**
 * @author black
 */
public interface SoftInfoService extends BaseService<SoftInfo, Long> {

    void remove(Soft soft);

    void create(Soft soft, String html);
}