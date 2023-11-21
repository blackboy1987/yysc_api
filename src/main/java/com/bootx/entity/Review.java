
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Review extends BaseEntity<Long> {

	/**
	 * 评分
	 */
	@JsonView(BaseView.class)
	@NotNull
	@Min(1)
	@Max(10)
	@Column(nullable = false, updatable = false)
	private Integer score;

	/**
	 * 内容
	 */
	@JsonView(BaseView.class)
	@Length(max = 200)
	@Column(updatable = false)
	private String content;

	/**
	 * 是否显示
	 */
	@Column(nullable = false)
	private Boolean isShow;

	/**
	 * IP
	 */
	@Column(nullable = false, updatable = false)
	private String ip;

	/**
	 * 会员
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	/**
	 * 商品
	 */
	@JsonView(BaseView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Soft soft;

	/**
	 * 回复
	 */
	@JsonView(BaseView.class)
	@OneToMany(mappedBy = "forReview", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createdDate asc")
	private Set<Review> replyReviews = new HashSet<>();

	/**
	 * 评论
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private Review forReview;

	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	public Soft getSoft() {
		return soft;
	}

	/**
	 * 设置商品
	 * 
	 * @param soft
	 *            商品
	 */
	public void setSoft(Soft soft) {
		this.soft = soft;
	}

	/**
	 * 获取回复
	 * 
	 * @return 回复
	 */
	public Set<Review> getReplyReviews() {
		return replyReviews;
	}

	/**
	 * 设置回复
	 * 
	 * @param replyReviews
	 *            回复
	 */
	public void setReplyReviews(Set<Review> replyReviews) {
		this.replyReviews = replyReviews;
	}

	/**
	 * 获取评论
	 * 
	 * @return 评论
	 */
	public Review getForReview() {
		return forReview;
	}

	/**
	 * 设置评论
	 * 
	 * @param forReview
	 *            评论
	 */
	public void setForReview(Review forReview) {
		this.forReview = forReview;
	}

}