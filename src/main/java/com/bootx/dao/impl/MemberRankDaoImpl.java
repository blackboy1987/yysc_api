
package com.bootx.dao.impl;

import com.bootx.dao.MemberRankDao;
import com.bootx.entity.MemberRank;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Repository
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, Long> implements MemberRankDao {

	@Override
	public MemberRank findDefault() {
		try {
			String jpql = "select memberRank from MemberRank memberRank where memberRank.isDefault = true";
			return entityManager.createQuery(jpql, MemberRank.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public MemberRank findByAmount(BigDecimal amount) {
		try {
			String jpql = "select memberRank from MemberRank memberRank where memberRank.isSpecial = false and memberRank.amount <= :amount order by memberRank.amount desc";
			return entityManager.createQuery(jpql, MemberRank.class).setParameter("amount", amount).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void clearDefault() {
		String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true";
		entityManager.createQuery(jpql).executeUpdate();
	}

	@Override
	public void clearDefault(MemberRank exclude) {
		Assert.notNull(exclude, "[Assertion failed] - exclude is required; it must not be null");

		String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true and memberRank != :exclude";
		entityManager.createQuery(jpql).setParameter("exclude", exclude).executeUpdate();
	}

}