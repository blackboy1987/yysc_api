
package com.bootx.service;

import com.bootx.common.Pageable;
import com.bootx.entity.Member;

import java.util.List;
import java.util.Map;

/**
 * @author black
 */
public interface MemberService extends BaseService<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Member findByUsername(String username);

	boolean usernameUnique(Long id, String username);


	Member getCurrent();

	void lock(Member member);

	void unLock(Member member);

    List<Map<String,Object>> search(String keywords, Pageable pageable);
}