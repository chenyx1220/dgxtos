package com.ruipengkj.common.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
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
	public <T extends Object> int insert(T entry) {
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
	public <T extends Object> int update(T entry, Condition condition) {
		try {
			String sql = SqlUtils.buildUpdate(entry, condition);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库更新失败", e);
		}
		
	}
	
	@Override
	public <T extends Object> int updateByPrimaryKey(T entry) {
		try {
			String sql = SqlUtils.buildUpdate(entry, null);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库更新失败", e);
		}
		
	}

	@Override
	public <T extends Object> int delete(T entry, Condition condition) {
		try {
			String sql = SqlUtils.buildDelete(entry, condition);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库删除失败", e);
		}
	}
	
	@Override
	public <T extends Object> int deleteByPrimaryKey(T entry) {
		try {
			String sql = SqlUtils.buildDelete(entry, null);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库删除失败", e);
		}
	}

	@Override
	public <T extends Object, E> E selectByPrimaryKey(T entry, Class<E> clazz) {
		E object = null;
		try {
//			Map<String, Object> entry = new HashMap<String, Object>();
//			entry.put("id", id);
			String sql = SqlUtils.buildSelect(entry, null);
			object = this.namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(entry), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库PrimaryKey查询对象失败", e);
		}
		return object;
	}

	@Override
	public <T extends Object, E> List<E> selectList(T entry, Condition condition, Class<E> clazz) {
		List<E> list = null;
		String sql = SqlUtils.buildSelect(entry, condition);
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库查询列表失败", e);
		}
		return list;
	}
	

	@Override
	public <T extends Object,E> E selectObject(T entry, Condition condition, Class<E> clazz) {
		E object = null;
		String sql = SqlUtils.buildSelect(entry, condition);
		try {
			if (this.decideParam(clazz)) {
				object = this.namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(entry), clazz);
			} else {
				object = this.namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(entry), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库查询对象失败", e);
		}
		return object;
	}
	
	@Override
	public <T extends Object> Integer count(T entry, Condition condition) {
		Integer count = 0;
		String sql = SqlUtils.buildSelect(entry, condition);
		try {
			count = this.namedParameterJdbcTemplate.queryForObject(sql, new BeanPropertySqlParameterSource(entry), Integer.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库统计数量失败", e);
		}
		return count;
	}

	@Override
	public <T extends Object> Map<String, ?> selectMap(T entry, Condition condition) {
		String sql = SqlUtils.buildSelect(entry, condition);
		try {
			return this.namedParameterJdbcTemplate.queryForMap(sql, new BeanPropertySqlParameterSource(entry));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库Map查询失败", e);
		}
	}

	@Override
	public <T extends Object> void batchInsert(T entry, List<?> obj) {
		String sql = SqlUtils.buildBatchInsert(entry);
		try {
			this.namedParameterJdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(obj.toArray()));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库批量操作失败", e);
		}
		
	}
	
	@Override
	public <T extends Object> void batchUpdate(T entry, Condition condition, List<?> obj) {
		String sql = SqlUtils.buildBatchUpdate(entry, condition);
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
