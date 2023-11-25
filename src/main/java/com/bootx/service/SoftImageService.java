
package com.bootx.service;

import com.bootx.entity.Soft;
import com.bootx.entity.SoftImage;

public interface SoftImageService extends BaseService<SoftImage, Long> {

    void remove(Soft soft);
}