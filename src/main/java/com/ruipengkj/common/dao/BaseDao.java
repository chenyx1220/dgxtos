package com.ruipengkj.common.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {
	
	/**
	 * 保存操作
	 * 
	 * @param sql
	 * @param entry
	 */
	<T> int insert(T entry);

	/**
	 * 条件更新
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	int update(Condition condition);

	/**
	 * 条件删除
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	int delete(Condition condition);
	
	
	
	/**
	 * 统计数量
	 * @param entry
	 * @return
	 */
	Integer count(Condition condition);

	/**
	 * 查询实体列表
	 * 
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	<E> List<E> selectList(Condition condition, Class<E> clazz);
	
	<E> List<E> selectList(String sql, Map<String, Object> params, Class<E> clazz);
	<E> List<E> selectList(String sql, Class<E> clazz);

	/**
	 * 查询实体
	 * 
	 * @param <T>
	 * @param sql
	 * @param objs
	 * @return
	 */
	<E> E selectObject(Condition condition, Class<E> clazz);

	/**
	 * 查询一个Map集合
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	Map<String, ?> selectMap(Condition condition);

	/**
	 * 批量添加操作
	 * 
	 * @param sql
	 * @param objList
	 */
	<T> void batchInsert(Class<T> clazz, List<T> objList);
	
	/**
	 * 批量更新操作
	 * 
	 * @param sql
	 * @param objList
	 */
	<T> void batchUpdate(Condition condition, List<T> objList);

}
