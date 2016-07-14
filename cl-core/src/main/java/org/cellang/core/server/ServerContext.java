package org.cellang.core.server;

import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.core.entity.EntitySession;
import org.h2.jdbcx.JdbcConnectionPool;

public class ServerContext {

	private EntitySession entityService;
	
	private String dbUrl;

	public ServerContext(EntitySession es) {
		this.entityService = es;
	}

	public EntitySession getEntityService() {

		return this.entityService;
	}

}
