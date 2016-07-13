package org.cellang.core.server;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.core.entity.EntityService;
import org.h2.jdbcx.JdbcConnectionPool;

public class ServerContext {

	private EntityService entityService;
	
	private String dbUrl;

	public ServerContext(EntityService es) {
		this.entityService = es;
	}

	public EntityService getEntityService() {

		return this.entityService;
	}

}
