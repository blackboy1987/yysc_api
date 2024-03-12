
package com.bootx.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Category;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.pojo.SoftPOJO;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface SoftService extends BaseService<Soft, Long> {

    Soft findByUrl(String href);

    void updateDownloads(Long id,int i);

    void create(SoftPOJO softPOJO, Member member);

    String getStatus(String status);

    List<Map<String, Object>> get(Pageable pageable, String orderBy, Long categoryId);

    List<Map<String, Object>> search(String keywords, Pageable pageable);

    void load(Soft soft);
    void load(Category category);

    void batchSave(Category category, List<Soft> soft);
}