
package com.bootx.dao.impl;

import com.bootx.dao.MessageDao;
import com.bootx.entity.Message;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements MessageDao {

}