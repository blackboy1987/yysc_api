
package com.bootx.dao.impl;

import com.bootx.dao.MemberOptLogDao;
import com.bootx.dao.OptLogDao;
import com.bootx.entity.MemberOptLog;
import com.bootx.entity.OptLog;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class MemberOptLogDaoImpl extends BaseDaoImpl<MemberOptLog, Long> implements MemberOptLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from MemberOptLog optLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}