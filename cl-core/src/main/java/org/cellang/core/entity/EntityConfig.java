package org.cellang.core.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

/**
 * A container that manage information about entity type.Which includes the
 * class of the entity bean, the table the property method etc.
 * 
 * @see EntityConfigFactory
 * @author wu
 *
 */
public class EntityConfig {

	private Class<? extends EntityObject> entityClass;

	private String tableName;

	private Constructor constuctor;

	private Map<String, Method> getMethodMap = new HashMap<String, Method>();

	private Map<String, Method> setMethodMap = new HashMap<String, Method>();

	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		// converterMap.put("", value)
	}

	private String createViewSql;

	public EntityConfig(Class<? extends EntityObject> cls, String tableName) {
		this.entityClass = cls;
		this.tableName = tableName;
		try {
			this.constuctor = this.entityClass.getConstructor();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		this.initGetSetMethods(this.entityClass);//
	}

	private void initGetSetMethods(Class cls) {
		if (cls.equals(Object.class)) {
			return;
		}

		for (Method m : cls.getDeclaredMethods()) {
			String name = m.getName();
			if (name.startsWith("get")) {
				String key = name.substring("get".length());
				String first = key.substring(0, 1).toLowerCase();
				key = first + key.substring(1);
				this.getMethodMap.put(key, m);
			} else if (name.startsWith("set")) {
				String key = name.substring("set".length());
				String first = key.substring(0, 1).toLowerCase();
				key = first + key.substring(1);
				this.setMethodMap.put(key, m);
			}
		}
		this.initGetSetMethods(cls.getSuperclass());//
	}

	public List<Method> getGetMethodList() {
		return new ArrayList<Method>(this.getMethodMap.values());
	}

	public Class<? extends EntityObject> getEntityClass() {
		return entityClass;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void fillInsert(Object entity, InsertRowOperation insert) {
		for (Map.Entry<String, Method> en : this.getMethodMap.entrySet()) {
			Object value;
			try {
				value = en.getValue().invoke(entity);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			insert.addValue(en.getKey(), value);//
		}
	}

	public Object newEntity() {
		try {
			return this.constuctor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public Object extractFrom(ResultSet rs) throws SQLException {
		Object entity = this.newEntity();
		for (Map.Entry<String, Method> en : this.setMethodMap.entrySet()) {
			Object value = rs.getObject(en.getKey());
			Class ptype = en.getValue().getParameterTypes()[0];
			Converter c = this.converterMap.get(ptype);
			if (c != null) {
				value = c.convert(value);
			}
			try {
				en.getValue().invoke(entity, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		return entity;
	}

	public void fillCreate(CreateTableOperation cto) {
		for (Map.Entry<String, Method> en : this.setMethodMap.entrySet()) {
			Class ptype = en.getValue().getParameterTypes()[0];
			String name = en.getKey();
			cto.addColumn(name, ptype);
		}
		cto.addPrimaryKey("id");//
	}

	public String getCreateViewSql() {
		return this.createViewSql;
	}

	public void setCreateViewSql(String sql) {
		this.createViewSql = sql;
	}

}
