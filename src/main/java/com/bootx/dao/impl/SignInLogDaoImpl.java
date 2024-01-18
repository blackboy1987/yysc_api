package com.bootx.dao.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.SignInLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.SignInLog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class SignInLogDaoImpl extends BaseDaoImpl<SignInLog,Long> implements SignInLogDao {
    @Override
    public Page<SignInLog> findPage(Pageable pageable, Member member) {
        if (member == null) {
            return Page.emptyPage(pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SignInLog> criteriaQuery = criteriaBuilder.createQuery(SignInLog.class);
        Root<SignInLog> root = criteriaQuery.from(SignInLog.class);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
        criteriaQuery.select(root);
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}
