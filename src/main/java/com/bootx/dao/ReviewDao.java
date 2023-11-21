
package com.bootx.dao;

import com.bootx.entity.Member;
import com.bootx.entity.Review;
import com.bootx.entity.Soft;

public interface ReviewDao extends BaseDao<Review, Long> {

	/**
	 * 查找评论数量
	 * 
	 * @param member
	 *            会员
	 * @param soft
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Member member, Soft soft, Boolean isShow);

	/**
	 * 计算商品总评分
	 * 
	 * @param soft
	 *            商品
	 * @return 商品总评分，仅计算显示评论
	 */
	long calculateTotalScore(Soft soft);

	/**
	 * 计算商品评分次数
	 * 
	 * @param soft
	 *            商品
	 * @return 商品评分次数，仅计算显示评论
	 */
	long calculateScoreCount(Soft soft);

}