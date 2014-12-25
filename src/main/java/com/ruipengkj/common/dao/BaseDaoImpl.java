package com.ruipengkj.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	protected static final int SQL_INSERT = 1;  
	protected static final int SQL_UPDATE = 2;  
	protected static final int SQL_DELETE = 3;
	protected static final int SQL_SELECT = 4;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public <T extends Object> int insert(T entry) {
		KeyHolder keyHolder = new GeneratedKeyHolder();  
		try {
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
	public <T extends Object> int update(T entry) {
		try {
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库更新失败", e);
		}
		
	}

	@Override
	public <T extends Object> int delete(T entry) {
		try {
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库删除失败", e);
		}
	}

	@Override
	public <T extends Object, E> List<E> selectList(T entry, Class<E> clazz) {
		List<E> list = null;
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
	public <T extends Object,E> E selectObject(T entry, Class<E> clazz) {
		E object = null;
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
	public <E> E selectObject(String sql, Class<E> clazz) {
		E object = null;
		try {
			if (this.decideParam(clazz)) {
				object = this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, clazz);
			} else {
				object = this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(clazz));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库查询对象失败", e);
		}
		return object;
	}
	
	@Override
	public <T extends Object> int count(T entry) {
		int count = 0;
		try {
			count = this.namedParameterJdbcTemplate.queryForInt(sql, new BeanPropertySqlParameterSource(entry));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库统计数量失败", e);
		}
		return count;
	}

	@Override
	public <T extends Object> Map<String, ?> selectMap(T entry) {
		try {
			return this.namedParameterJdbcTemplate.queryForMap(sql, new BeanPropertySqlParameterSource(entry));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库Map查询失败", e);
		}
	}

	@Override
	public void batchOperate(List<?> obj) {
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

	@Override
	public int deleteByPrimaryKey(Object id) {
		try {
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("id", id);
			int count = namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库删除失败", e);
		}
	}

	@Override
	public <E> E selectByPrimaryKey(Object id, Class<E> clazz) {
		E object = null;
		try {
			Map<String, Object> entry = new HashMap<String, Object>();
			entry.put("id", id);
			object = this.namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(entry), ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoException("数据库PrimaryKey查询对象失败", e);
		}
		return object;
	}
	
	protected <T extends Object> String buildSql(int sqlFlag, T entry) {
		StringBuffer sql = new StringBuffer();
		Class<? extends Object> clazz = entry.getClass();
		if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sb.append("SELECT * FROM "); 
            sb.append(table.tableName()); 
        } 
        Field[] fields = entry.getClass().getAnnotation(annotationClass).getDeclaredFields();
		switch (sqlFlag) {
		case SQL_INSERT:
			break;
		case SQL_UPDATE:
			break;
		case SQL_DELETE:
			break;
		default:
			break;
		}
        if (sqlFlag.equals(SQL_INSERT)) {  
            sql.append(" INSERT INTO " + entityClass.getSimpleName());  
            sql.append("(");  
            for (int i = 0; fields != null && i < fields.length; i++) {  
                fields[i].setAccessible(true); // 暴力反射  
                String column = fields[i].getName();  
                sql.append(column).append(",");  
            }  
            sql = sql.deleteCharAt(sql.length() - 1);  
            sql.append(") VALUES (");  
            for (int i = 0; fields != null && i < fields.length; i++) {  
                sql.append("?,");  
            }  
            sql = sql.deleteCharAt(sql.length() - 1);  
            sql.append(")");  
        } else if (sqlFlag.equals(SQL_UPDATE)) {  
            sql.append(" UPDATE " + entityClass.getSimpleName() + " SET ");  
            for (int i = 0; fields != null && i < fields.length; i++) {  
                fields[i].setAccessible(true); // 暴力反射  
                String column = fields[i].getName();  
                if (column.equals("id")) { // id 代表主键  
                    continue;  
                }  
                sql.append(column).append("=").append("?,");  
            }  
            sql = sql.deleteCharAt(sql.length() - 1);  
            sql.append(" WHERE id=?");  
        } else if (sqlFlag.equals(SQL_DELETE)) {  
            sql.append(" DELETE FROM " + entityClass.getSimpleName() + " WHERE id=?");  
        }  
        System.out.println("SQL=" + sql);  
        return sql.toString();  
	}
	
	
}
