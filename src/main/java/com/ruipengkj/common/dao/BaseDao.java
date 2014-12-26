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
	<T extends Object> int update(T entry, Condition condition);

	/**
	 * 条件删除
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	<T extends Object> int delete(T entry, Condition condition);
	
	/**
	 * 主键删除
	 * 
	 * @param sql
	 * @param id
	 * @return
	 */
	<T extends Object> int deleteByPrimaryKey(T entry);
	
	/**
	 * 主键更新
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	<T extends Object> int updateByPrimaryKey(T entry);
	
	/**
	 * 主键查询
	 * @param id
	 * @param clazz
	 * @return
	 */
	<T extends Object, E> E selectByPrimaryKey(T entry, Class<E> clazz);
	
	/**
	 * 统计数量
	 * @param entry
	 * @return
	 */
	<T extends Object> Integer count(T entry, Condition condition);

	/**
	 * 查询实体列表
	 * 
	 * @param sql
	 * @param className
	 * @param obj
	 * @return
	 */
	<T extends Object, E> List<E> selectList(T entry, Condition condition, Class<E> clazz);

	/**
	 * 查询实体
	 * 
	 * @param <T>
	 * @param sql
	 * @param objs
	 * @return
	 */
	<T extends Object,E> E selectObject(T entry, Condition condition, Class<E> clazz);

	/**
	 * 查询一个Map集合
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 */
	<T extends Object> Map<String, ?> selectMap(T entry, Condition condition);

	/**
	 * 批量添加操作
	 * 
	 * @param sql
	 * @param objList
	 */
	<T extends Object> void batchInsert(T entry, List<?> objList);
	
	/**
	 * 批量更新操作
	 * 
	 * @param sql
	 * @param objList
	 */
	<T extends Object> void batchUpdate(T entry, Condition condition, List<?> objList);

}
