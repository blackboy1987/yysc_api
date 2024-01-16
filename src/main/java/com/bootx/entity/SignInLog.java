
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

/**
 * @author black
 */
@Entity
public class SignInLog extends BaseEntity<Long> {

	/**
	 * IP
	 */
	@Column(nullable = false, updatable = false)
	@JsonView({PageView.class})
	private String ip;

	/**
	 * 用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@JsonView({PageView.class})
	private Long rewardPoints;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Long getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Long rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	@Transient
	@JsonView({PageView.class})
	public String getUsername(){
		if(member!=null){
			return member.getUsername();
		}
		return null;
	}

	@Transient
	@JsonView({PageView.class})
	public Long getMemberId(){
		if(member!=null){
			return member.getId();
		}
		return null;
	}
}