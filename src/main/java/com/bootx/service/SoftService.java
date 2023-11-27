
package com.bootx.service;

import com.bootx.entity.Soft;

public interface SoftService extends BaseService<Soft, Long> {

    Soft findByUrl(String href);

    void updateDownloads(Long id,int i);
}