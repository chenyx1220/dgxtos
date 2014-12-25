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
	<T extends Object> int insert(T entry);

	/**
	 * 条件更新
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	<T extends Object> int update(T entry);

	/**
	 * 条件删除
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	<T extends Object> int delete(T entry);
	
	/**
	 * 主键删除
	 * 
	 * @param sql
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Object id);
	
	/**
	 * 主键更新
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	<T extends Object> int updateByPrimaryKey(Object id, T entry);
	
	/**
	 * 主键查询
	 * @param id
	 * @param clazz
	 * @return
	 */
	<E> E selectByPrimaryKey(Object id, Class<E> clazz);
	
	/**
	 * 统计数量
	 * @param entry
	 * @return
	 */
	<T extends Object> int count(T entry);

	/**
	 * 查询实体列表
	 * 
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	<T extends Object, E> List<E> selectList(T entry, Class<E> clazz);

	/**
	 * 查询实体
	 * 
	 * @param <T>
	 * @param sql
	 * @param objs
	 * @return
	 */
	<T extends Object,E> E selectObject(T entry, Class<E> clazz);

	/**
	 * 查询一个Map集合
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	<T extends Object> Map<String, ?> selectMap(T entry);

	/**
	 * 批量操作
	 * 
	 * @param sql
	 * @param objList
	 */
	<T extends Object> void batchOperate(T entry, List<?> objList);

}
