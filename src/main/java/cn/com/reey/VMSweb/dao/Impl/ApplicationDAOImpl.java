/**   
 * filename：UserDAOImpl.java
 *   
 * date：2016年4月14日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.dao.Impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.reey.VMSweb.dao.ApplicationDAO;
import cn.com.reey.VMSweb.entity.Application;
import cn.com.reey.VMSweb.util.Constant;

@Repository("applicationDao")
public class ApplicationDAOImpl extends BaseDAOImpl<Application> implements ApplicationDAO {

	@Override
	public List<Application> getExportData(Date startTime, Date endTime,
			Integer type) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		criteria.add(Restrictions.between("applicationstarttime", startTime, endTime));
		if(type == Constant.EXPORT_TYPE_PASS) {
			criteria.add(Restrictions.eq("applicationispass", Constant.PASS_STATE_YES));
			
		}
		return findEntitysByDetachedCriteria(criteria, Integer.MAX_VALUE, 0);
	}

	@Override
	public List<Application> getExportData(Date startTime, Date endTime,
			Integer type, String order, String sort) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Application.class);
		criteria.add(Restrictions.between("applicationstarttime", startTime, endTime));
		if(type == Constant.EXPORT_TYPE_PASS) {
			criteria.add(Restrictions.eq("applicationispass", Constant.PASS_STATE_YES));
			
		}
		
		if(sort != null){
			String[] sorts = sort.split("\\.");
			String tempName = "";
			for(int i = 0; i < sorts.length-1; i++) {
				if(sorts[i] != null){
					criteria.createAlias(tempName + sorts[i], sorts[i]);
					tempName = sorts[i] + ".";
				}
			}
			if("desc".equalsIgnoreCase(order)) {
				criteria.addOrder(Order.desc(sort));
			} else {
				criteria.addOrder(Order.asc(sort));
			}
		} else {
			criteria.addOrder(Order.asc("applicationstarttime"));
		}
		return findEntitysByDetachedCriteria(criteria, Integer.MAX_VALUE, 0);
	}


	
}