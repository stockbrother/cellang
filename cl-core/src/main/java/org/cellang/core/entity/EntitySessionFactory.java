
package org.cellang.core.entity;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;

public interface EntitySessionFactory {

	public JdbcDataAccessTemplate getDataAccessTemplate();

	/**
	 * Open a session contains a SQL connection.need to be closed by caller.
	 * 
	 * @return
	 */
	public EntitySession openSession();

	/**
	 * Execute entity operation in a newly created session. It will be commit
	 * 
	 * @param op
	 * @return
	 */
	public <T> T execute(EntityOp<T> op);

	public <T extends EntityObject> void save(T entity);

	public <T> T execute(JdbcOperation<T> op);

	/**
	 * 
	 * @return
	 */
	public EntityConfigFactory getEntityConfigFactory();

	public DataVersion getDataVersion();

	public void close();

	public <T extends EntityObject> T getEntity(Class<T> cls, String id);

}