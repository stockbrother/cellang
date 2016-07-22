package org.cellang.core.entity;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;

/**
 * 
 * @author wu
 *
 */
public interface EntitySession {

	public void close(boolean commit);

	public <T> T getSingle(Class cls, String field, Object arg);

	public <T> T getSingle(Class cls, String[] fields, Object[] args);

	public <T extends EntityObject> List<T> getList(Class<T> cls);

	public <T extends EntityObject> List<T> getList(Class<T> cls, String field, Object value);

	public <T extends EntityObject> List<T> getList(Class<T> cls, String[] fields, Object[] args);

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String field, Object arg);

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls);

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String[] fields, Object[] args);

	public void saveAll(List<EntityObject> seList);

	public void saveAll(Iterator<EntityObject> eoIt);

	public void save(EntityObject se);
	
	public <T extends EntityObject> long delete(Class<T> cls);
	
	public <T extends EntityObject> long delete(Class<T> cls, String[] strings, Object[] objects);

	public EntitySessionFactory getFactory();

	public EntityConfigFactory getEntityConfigFactory();

	/**
	 * Remove all data from db.
	 */
	public void clear();

	public <T> T execute(JdbcOperation<T> op);
	
	public Connection getConnection();

	public void rollback();

	public void commit();

	public JdbcDataAccessTemplate getDataAccessTemplate();

	
	

}
