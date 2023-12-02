
package com.bootx.service;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftExt;
import com.bootx.entity.SoftImage;

/**
 * @author black
 */
public interface SoftExtService extends BaseService<SoftExt, Long> {

    void remove(Soft soft);

    void create(SoftExt softExt);
}