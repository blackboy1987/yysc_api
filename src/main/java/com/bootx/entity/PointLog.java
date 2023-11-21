
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
public class PointLog extends BaseEntity<Long> {

	/**
	 * 类型
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Integer type;

	/**
	 * 获取积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long credit;

	/**
	 * 当前积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long beforePoint;

	/**
	 * 当前积分
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false, updatable = false)
	private Long afterPoint;

	/**
	 * 备注
	 */
	@JsonView(BaseView.class)
	@Column(updatable = false)
	private String memo;

	/**
	 * 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Member member;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCredit() {
		return credit;
	}

	public void setCredit(Long credit) {
		this.credit = credit;
	}

	public Long getBeforePoint() {
		return beforePoint;
	}

	public void setBeforePoint(Long beforePoint) {
		this.beforePoint = beforePoint;
	}

	public Long getAfterPoint() {
		return afterPoint;
	}

	public void setAfterPoint(Long afterPoint) {
		this.afterPoint = afterPoint;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}