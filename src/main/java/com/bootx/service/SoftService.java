
package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.pojo.SoftPOJO;

/**
 * @author black
 */
public interface SoftService extends BaseService<Soft, Long> {

    Soft findByUrl(String href);

    void updateDownloads(Long id,int i);

    void create(SoftPOJO softPOJO, Member member);

    String getStatus(String status);
}