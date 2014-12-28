package com.ruipengkj.common.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDaoImpl implements BaseDao {
	
	final static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public <T> int insert(T entry) {
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		try {
			String sql = SqlUtils.buildInsert(entry);
			/*
			 * SqlParameterSource的两个主要实现MapSqlParameterSource和BeanPropertySqlParameterSource 
			 */
			namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry), keyHolder);
			return keyHolder.getKey().intValue();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库插入失败", e);
		}
	}

	@Override
	public int update(Condition condition) {
		try {
			String sql = SqlUtils.buildUpdate(condition);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(condition.getEntity()));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库更新失败", e);
		}
		
	}
	
	@Override
	public int delete(Condition condition) {
		try {
			String sql = SqlUtils.buildDelete(condition);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(condition.getEntity()));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库删除失败", e);
		}
	}
	


	@Override
	public <E> List<E> selectList(Condition condition, Class<E> clazz) {
		List<E> list = null;
		Object entry = condition.getEntity();
		String sql = SqlUtils.buildSelect(condition);
		logger.debug(sql);
		try {
			if (this.decideParam(clazz)) {
				if (entry == null) {
					list = this.namedParameterJdbcTemplate.getJdbcOperations().queryForList(sql, clazz);
				} else {
					list = this.namedParameterJdbcTemplate.queryForList(sql, new BeanPropertySqlParameterSource(entry), clazz);
				}
			} else {
				if (entry == null) {
					list = this.namedParameterJdbcTemplate.getJdbcOperations().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz));
				} else {
					list = this.namedParameterJdbcTemplate.query(sql, new BeanPropertySqlParameterSource(entry), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
				}
				
			}
			
			if (this.decideParam(clazz)) {
				
			} else {
				
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库查询失败", e);
			}
		}
		return list;
	}
	

	@Override
	public <E> E selectObject(Condition condition, Class<E> clazz) {
		E object = null;
		String sql = SqlUtils.buildSelect(condition);
		logger.debug(sql);
		try {
			if (this.decideParam(clazz)) {
				object = this.namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(condition.getExpressions()), clazz);
			} else {
				object = this.namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(condition.getExpressions()), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
			}
			
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库查询失败", e);
			}
		}
		return object;
	}
	
	@Override
	public <E> List<E> selectList(String sql, Class<E> clazz) {
		logger.debug(sql);
		List<E> list = null;
		try {
			if (this.decideParam(clazz)) {
				list = this.namedParameterJdbcTemplate.getJdbcOperations().queryForList(sql, clazz);
			} else {
				list = this.namedParameterJdbcTemplate.getJdbcOperations().query(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz));
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库查询失败", e);
			}
		}
		return list;
	}

	@Override
	public <E> List<E> selectList(String sql, Map<String, Object> params, Class<E> clazz) {
		List<E> list = null;
		try {
			if (this.decideParam(clazz)) {
				list = this.namedParameterJdbcTemplate.queryForList(sql, new MapSqlParameterSource(params), clazz);
			} else {
				list = this.namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(params), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库查询失败", e);
			}
		}
		return list;
	}

	@Override
	public Map<String, ?> selectMap(Condition condition) {
		String sql = SqlUtils.buildSelect(condition);
		Map<String, Object> map = null;
		try {
			map = this.namedParameterJdbcTemplate.queryForMap(sql, new BeanPropertySqlParameterSource(condition.getEntity()));
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库查询失败", e);
			}
		}
		return map;
	}
	
	@Override
	public Integer count(Condition condition) {
		Integer count = 0;
		String sql = SqlUtils.buildSelect(condition);
		try {
			count = this.namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(condition.getEntity()), Integer.class);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			Throwable te = e.getCause();
			if (te instanceof SQLException) {
				throw new DaoException("数据库统计失败", e);
			}
		}
		return count;
	}

	@Override
	public <T> void batchInsert(Class<T> clazz, List<T> obj) {
		String sql = SqlUtils.buildBatchInsert(clazz);
		try {
			this.namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(obj.toArray()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库批量操作失败", e);
		}
		
	}
	
	@Override
	public <T> void batchUpdate(Condition condition, List<T> obj) {
		String sql = SqlUtils.buildBatchUpdate(condition);
		try {
			this.namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(obj.toArray()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库批量操作失败", e);
		}
		
	}
	
	private boolean decideParam(Class<?> clazz) {
		Class<?>[] classArray = {Byte.class, Boolean.class, Short.class, Character.class, String.class, Integer.class, Long.class, Float.class, Double.class};
		boolean isBaseParam = false;
		for (Class<?> cl : classArray) {
			if (cl.getSimpleName().equals(clazz.getSimpleName())) {
				isBaseParam = true;
				break;
			}
		}
		return isBaseParam;
	}
	
	
	
}
