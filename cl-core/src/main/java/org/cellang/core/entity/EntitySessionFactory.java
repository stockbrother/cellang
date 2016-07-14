package org.cellang.core.entity;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;

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

	/**
	 * 
	 * @return
	 */
	public EntityConfigFactory getEntityConfigFactory();

	public void close();

	public boolean isNew();

}
