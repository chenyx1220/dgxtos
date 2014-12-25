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
    private static final Logger LOG = LoggerFactory.getLogger(SqlUtils.class);

    /**
     * 构建insert语句
     *
     * @param entity 实体映射对象
     * @param nameHandler 名称转换处理器
     * @return
     */
    public static SqlContext buildInsertSql(Object entity) {
    	StringBuilder sql = new StringBuilder("insert into ");
    	
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        List<Object> params = new ArrayList<Object>();

        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        sql.append("(");
        StringBuilder args = new StringBuilder();
        args.append("(");
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            sql.append(NameUtils.toUnderlineName(pd.getName()));
            args.append("?");
            params.add(value);
            sql.append(",");
            args.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        args.deleteCharAt(args.length() - 1);
        args.append(")");
        sql.append(")");
        sql.append(" values ");
        sql.append(args);
        return new SqlContext(sql, params);
    }

    /**
     * 构建更新sql
     * 
     * @param entity
     * @param nameHandler
     * @return
     */
    public static SqlContext buildUpdateSql(Object entity) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("update ");
        Class<?> clazz = entity.getClass();
        if (clazz.isAnnotationPresent(Table.class)) { 
			Table table = (Table) clazz.getAnnotation(Table.class); 
            sql.append(table.tableName()); 
        } else {
        	throw new DaoException("实体类没有注解@Table");
        }
        
        List<Object> params = new ArrayList<Object>();
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
        
        sql.append(" set ");
        Object primaryValue = null;
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            String columnName = NameUtils.toUnderlineName(pd.getName());
            if ("id".equalsIgnoreCase(columnName)) {
                primaryValue = value;
                continue;
            }
            sql.append(columnName);
            sql.append(" = ");
            sql.append("?");
            params.add(value);
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where id = ?");
        params.add(primaryValue);
        return new SqlContext(sql, params);
    }

    /**
     * 构建查询条件
     * 
     * @param entity
     * @param nameHandler
     */
    public static SqlContext buildQueryCondition(Object entity) {
        //获取属性信息
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(entity.getClass());
        StringBuilder condition = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        int count = 0;
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null) {
                continue;
            }
            if (count > 0) {
                condition.append(" and ");
            }
            condition.append(NameUtils.toUnderlineName(pd.getName()));
            condition.append(" = ?");
            params.add(value);
            count++;
        }
        return new SqlContext(condition, params);
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
            LOG.error("获取属性值失败", e);
            throw new DaoException("获取属性值失败");
        }
    }
}
