package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.DicDao;
import com.bootx.entity.Dic;
import com.bootx.entity.DicCategory;
import com.bootx.entity.PointLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class DicDaoImpl extends BaseDaoImpl<Dic,Long> implements DicDao {
    @Override
    public Page<Dic> findPage(Pageable pageable, DicCategory dicCategory) {
        if (dicCategory == null) {
            return Page.emptyPage(pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Dic> criteriaQuery = criteriaBuilder.createQuery(Dic.class);
        Root<Dic> root = criteriaQuery.from(Dic.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("dicCategory"), dicCategory));
        return super.findPage(criteriaQuery, pageable);
    }
}
