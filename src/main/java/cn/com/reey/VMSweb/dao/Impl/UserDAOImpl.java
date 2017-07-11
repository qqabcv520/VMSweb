/**   
 * filename：UserDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao.Impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.reey.VMSweb.dao.UserDAO;
import cn.com.reey.VMSweb.entity.Users;

@Repository("userDao")
public class UserDAOImpl extends BaseDAOImpl<Users> implements UserDAO {

	@Override
	public Users findByUsername(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
		criteria.add(Restrictions.eq("username", username));
		return findEntityByDetachedCriteria(criteria);
	}

	public Users findByName(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
		criteria.add(Restrictions.eq("username", username));
		return findEntityByDetachedCriteria(criteria);
	}

	@Override
	public Users findByUsersignid(String usersignid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
		criteria.add(Restrictions.eq("usersignid", usersignid));
		return findEntityByDetachedCriteria(criteria);
	}
}