/**   
 * filename：EntityService.java
 *   
 * date：2016年4月19日
 * Copyright reey Corporation 2016 
 *   
 */
package cn.com.reey.VMSweb.service;

import java.util.List;

import cn.com.reey.VMSweb.util.jsonEntity.JsonData;

public interface BaseEntityService<E> {

	/**   
	 * 获取对应实体类的数据，JSON形式
	 * @author 范子才
	 * @param limit 每页的个数
	 * @param offset 第几页
	 * @param order 排序，asc或desc
	 * @param sort 排序字段
	 * @return JSON字符串
	 * @version 2016年4月19日 下午11:23:40
	 */
	JsonData getJsonData(int limit, int offset, String order, String sort);
	
	
	/**
	 * 保存/更新
	 * @param entity
	 * @throws ValidateException 
	 */
	JsonData saveOrUpdate(E entity);
	
	/**
	 * 获取对应实体信息
	 * @param id 主键
	 * @return
	 */
	JsonData get(int id);
	

	/**
	 * 实体保存
	 * @param entity
	 * @throws Exception
	 */
	JsonData save(E entity);
	
	/**
	 * 实体更新
	 * @param entity
	 * @throws Exception
	 */
	JsonData update(E entity);
	
	/**
	 * 删除，入参为id主键
	 * @param id
	 * @throws Exception
	 */
	JsonData deleteById(int id);
	
	/**   
	 * 批量删除，入参为id主键数组
	 * @author 范子才
	 * @param idArr
	 * @return JSON
	 * @throws Exception
	 * @version 2016年4月25日 上午10:46:04
	 */
	JsonData deleteByIdArray(Integer[] idArr);
	
	
	/**
	 * 获取全部实体信息
	 * @return
	 */
	JsonData getAll();
	
	/**
	 * 根据对应列获取全部实体信息，排序
	 * @param orderBy 排序字段
	 * @return
	 */
	JsonData getAll(String orderBy);
	
	
	
	
}
