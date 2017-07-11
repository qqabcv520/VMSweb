/**   
 * filename：BaseEntityService.java
 *   
 * date：2016年4月22日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service.impl;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;

import cn.com.reey.VMSweb.dao.BaseDAO;
import cn.com.reey.VMSweb.service.BaseEntityService;
import cn.com.reey.VMSweb.util.MyStatus;
import cn.com.reey.VMSweb.util.MyUtils;
import cn.com.reey.VMSweb.util.jsonEntity.JsonData;
import cn.com.reey.VMSweb.util.jsonEntity.TableData;

public abstract class BaseEntityServiceImpl<E> implements BaseEntityService<E> {

	@Autowired
	private BaseDAO<E> baseDao;

	private Class<E> persistentClass;

	private static final Logger logger = LoggerFactory.getLogger(BaseEntityServiceImpl.class);


	@SuppressWarnings("unchecked")
	public BaseEntityServiceImpl(){  
		//getClass() 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的超类的 Class。  
		this.persistentClass = (Class<E>)MyUtils.getSuperClassGenricType(getClass(), 0);  
	}

	@Override
	@Transactional
	public JsonData saveOrUpdate(E entity) {
		JsonData jsonData = new JsonData();
		baseDao.saveOrUpdate(entity);
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}

	@Override
	public JsonData get(int id) {

		JsonData jsonData = new JsonData();
		jsonData.setData(baseDao.get(id));
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}

	@Override
	public JsonData save(E entity) {
		JsonData jsonData = new JsonData();
		baseDao.save(entity);
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}

	@Override
	public JsonData update(E entity) {
		JsonData jsonData = new JsonData();
		baseDao.update(entity);
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;

	}

	@Override
	public JsonData deleteById(int id){
		JsonData jsonData = new JsonData();
		baseDao.deleteById(id);
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}

	@Override
	public JsonData deleteByIdArray(Integer[] idArr) {
		JsonData jsonData = new JsonData();
		if(idArr != null){
			jsonData.setData(baseDao.deleteByIdArray(idArr));
			jsonData.setStatusCode(MyStatus.OK);
			
		} else {
			jsonData.setStatusCode(MyStatus.PARAMETER_EXCEPTION);
		}
		
		
		return jsonData;
	}


	@Override
	public JsonData getAll() {

		JsonData jsonData = new JsonData();
		jsonData.setData(baseDao.getAll());
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}

	@Override
	public JsonData getAll(String orderBy) {
		JsonData jsonData = new JsonData();
		jsonData.setData(baseDao.getAll(orderBy));
		jsonData.setStatusCode(MyStatus.OK);
		return jsonData;
	}


	@Override
	public JsonData getJsonData(int limit, int offset, String order, String sort) {


		TableData tableData = new TableData();
		
		//获取数据
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(persistentClass);
		
		
		if(sort != null){
			String[] sorts = sort.split("\\.");
			String tempName = "";
			for(int i = 0; i < sorts.length-1; i++) {
				if(sorts[i] != null){
					detachedCriteria.createAlias(tempName + sorts[i], sorts[i]);
					tempName = sorts[i] + ".";
				}
			}
			if("desc".equalsIgnoreCase(order)) {
				detachedCriteria.addOrder(Order.desc(sort));
			} else {
				detachedCriteria.addOrder(Order.asc(sort));
			}
		}
		
		List<E> list = baseDao.findEntitysByDetachedCriteria(detachedCriteria, limit, offset);
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		tableData.setRows(jsonArray);
		
		
		//获取总数据数
		DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(persistentClass);
		int count = baseDao.getRowCountByDetachedCriteria(detachedCriteria2);
		tableData.setTotal(count);
		
		
		JsonData jsonData = new JsonData();
		jsonData.setStatusCode(MyStatus.OK);
		jsonData.setData(tableData);
		return jsonData;
	}
	
	
}
