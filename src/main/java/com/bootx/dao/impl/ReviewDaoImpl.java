
package com.bootx.dao.impl;

import com.bootx.dao.ReviewDao;
import com.bootx.entity.Member;
import com.bootx.entity.Review;
import com.bootx.entity.Soft;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author black
 */
@Repository
public class ReviewDaoImpl extends BaseDaoImpl<Review, Long> implements ReviewDao {

	@Override
	public Long count(Member member, Soft soft, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNull(root.get("forReview")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (soft != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("soft"), soft));
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	@Override
	public long calculateTotalScore(Soft soft) {
		String jpql = "select sum(review.score) from Review review where review.soft = :soft and review.isShow = :isShow and review.forReview is null";
		Long totalScore = entityManager.createQuery(jpql, Long.class).setParameter("soft", soft).setParameter("isShow", true).getSingleResult();
		return totalScore != null ? totalScore : 0L;
	}

	@Override
	public long calculateScoreCount(Soft soft) {
		String jpql = "select count(*) from Review review where review.soft = :soft and review.isShow = :isShow and review.forReview is null";
		return entityManager.createQuery(jpql, Long.class).setParameter("soft", soft).setParameter("isShow", true).getSingleResult();
	}

}