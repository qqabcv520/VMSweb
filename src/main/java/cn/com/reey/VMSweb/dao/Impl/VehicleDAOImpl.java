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

import cn.com.reey.VMSweb.dao.VehicleDAO;
import cn.com.reey.VMSweb.entity.Users;
import cn.com.reey.VMSweb.entity.Vehicle;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

@Repository("vehicleDao")
public class VehicleDAOImpl extends BaseDAOImpl<Vehicle> implements VehicleDAO {

	@Override
	public Vehicle findByPlatenumber(String platenumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Vehicle.class);
		criteria.add(Restrictions.eq("vehicleplatenumber", platenumber));
		return findEntityByDetachedCriteria(criteria);
	}

	
}