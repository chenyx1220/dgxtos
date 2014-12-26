package com.ruipengkj.common.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ruipengkj.common.util.NameUtils;

/**
 * 构造动态SQL
 * @author Squall
 *
 */
public class SqlUtils {
    private static final Logger logger = LoggerFactory.getLogger(SqlUtils.class);

    /**
     * 构建insert语句
     *
     * @param entity 实体映射对象
     * @return
     */
    public static String buildInsert(Object entity) {
    	StringBuilder sql = new StringBuilder("insert into ");
    	
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        if (pds.length == 0) {
        	throw new DaoException(clazz.getSimpleName() + "-实体没有字段");
        }
        sql.append('(');
        StringBuilder args = new StringBuilder();
        args.append('(');
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            sql.append(NameUtils.toUnderlineName(pd.getName()));
            sql.append(',');
            args.append(':');
            args.append(pd.getName());
            args.append(',');
        }
        sql.deleteCharAt(sql.length() - 1);
        args.deleteCharAt(args.length() - 1);
        args.append(')');
        sql.append(')');
        sql.append(" values ");
        sql.append(args);
        return sql.toString();
    }

    /**
     * 构建更新sql
     * 
     * @param entity
     * @return
     */
    public static String buildUpdate(Object entity, Condition condition) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ");
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        if (pds.length == 0) {
        	throw new DaoException(clazz.getSimpleName() + "-实体没有字段");
        }
        sql.append(" set ");
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            String columnName = NameUtils.toUnderlineName(pd.getName());
            if ("id".equalsIgnoreCase(columnName)) {
                continue;
            }
            sql.append(columnName);
            sql.append(" = ");
            sql.append(':');
            sql.append(pd.getName());
            sql.append(',');
        }
        sql.deleteCharAt(sql.length() - 1);
        if (condition != null && condition.getExpressions().size() > 0) {
        	sql.append(" where ");
        	List<String> expressions = condition.getConditions();
        	for (String expression : expressions) {
        		sql.append(expression);
        	}
        } else {
        	sql.append(" where id = :id");
        }
        return sql.toString();
    }
    
    /**
     * 构造删除SQL
     * @param entity
     * @param condition
     * @return
     */
    public static String buildDelete(Object entity, Condition condition) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("delete from ");
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        if (condition != null && condition.getExpressions().size() > 0) {
        	sql.append(" where ");
        	List<String> expressions = condition.getConditions();
        	for (String expression : expressions) {
        		sql.append(expression);
        	}
        } else {
        	sql.append(" where id = :id");
        }
        return sql.toString();
    }
    
    /**
     * 构造查询SQL
     * @param entity
     * @param condition
     * @return
     */
    public static String buildSelect(Object entity, Condition condition) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("select ");
    	if (condition != null && condition.getFields().size() > 0) {
    		List<String> fields = condition.getFields();
    		for (String field : fields) {
    			sql.append(field);
    			sql.append(',');
    		}
    		sql.deleteCharAt(sql.length() - 1);
    	} else {
    		sql.append("*");
    	}
    	sql.append(" from ");
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        if (condition != null && condition.getExpressions().size() > 0) {
        	sql.append(" where ");
        	List<String> expressions = condition.getConditions();
        	for (String expression : expressions) {
        		sql.append(expression);
        	}
        } else {
        	sql.append(" where id = :id");
        }
        return sql.toString();
    }
    
    /**
     * 构建批量insert语句
     *
     * @param entity 实体映射对象
     * @return
     */
    public static String buildBatchInsert(Object entity) {
    	StringBuilder sql = new StringBuilder("insert into ");
    	
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        if (pds.length == 0) {
        	throw new DaoException(clazz.getSimpleName() + "-实体没有字段");
        }
        sql.append('(');
        StringBuilder args = new StringBuilder();
        args.append('(');
        for (PropertyDescriptor pd : pds) {
            sql.append(NameUtils.toUnderlineName(pd.getName()));
            sql.append(',');
            args.append(':');
            args.append(pd.getName());
            args.append(',');
        }
        sql.deleteCharAt(sql.length() - 1);
        args.deleteCharAt(args.length() - 1);
        args.append(')');
        sql.append(')');
        sql.append(" values ");
        sql.append(args);
        return sql.toString();
    }
    
    /**
     * 构建批量更新sql
     * 
     * @param entity
     * @return
     */
    public static String buildBatchUpdate(Object entity, Condition condition) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ");
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        if (pds.length == 0) {
        	throw new DaoException(clazz.getSimpleName() + "-实体没有字段");
        }
        sql.append(" set ");
        for (PropertyDescriptor pd : pds) {
            String columnName = NameUtils.toUnderlineName(pd.getName());
            if ("id".equalsIgnoreCase(columnName)) {
                continue;
            }
            sql.append(columnName);
            sql.append(" = ");
            sql.append(':');
            sql.append(pd.getName());
            sql.append(',');
        }
        sql.deleteCharAt(sql.length() - 1);
        if (condition != null && condition.getExpressions().size() > 0) {
        	sql.append(" where ");
        	List<String> expressions = condition.getConditions();
        	for (String expression : expressions) {
        		sql.append(expression);
        	}
        } else {
        	sql.append(" where id = :id");
        }
        return sql.toString();
    }

    /**
     * 获取属性值
     *
     * @param readMethod
     * @param entity
     * @return
     */
    private static Object getReadMethodValue(Method readMethod, Object entity) {
        if (readMethod == null) {
            return null;
        }
        try {
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            return readMethod.invoke(entity);
        } catch (Exception e) {
            logger.error("获取属性值失败", e);
            throw new DaoException("获取属性值失败");
        }
    }
}
