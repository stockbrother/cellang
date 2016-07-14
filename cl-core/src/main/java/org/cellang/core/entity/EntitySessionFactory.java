package org.cellang.core.entity;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;

public interface EntitySessionFactory {

	public JdbcDataAccessTemplate getDataAccessTemplate();

	public EntitySession openSession();

	public <T> T execute(EntityOp<T> op);

	public EntityConfigFactory getEntityConfigFactory();

	public void close();

	public boolean isNew();

}
