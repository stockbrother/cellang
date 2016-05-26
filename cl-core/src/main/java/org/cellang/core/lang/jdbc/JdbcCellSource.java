package org.cellang.core.lang.jdbc;

import java.util.List;

import org.cellang.core.common.Configuration;
import org.cellang.core.common.jdbc.ConnectionPoolWrapper;
import org.cellang.core.lang.AbstractCellSource;
import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.jdbc.operation.CellInsertOperation;
import org.cellang.core.lang.jdbc.operation.ClearDBOperation;
import org.cellang.core.lang.jdbc.operation.DropDBOperation;
import org.cellang.core.lang.jdbc.operation.InitialDBOperation;
import org.cellang.core.lang.jdbc.operation.RelationInsertOperation;
import org.cellang.core.lang.jdbc.operation.SbSelectCellSql;
import org.h2.jdbcx.JdbcConnectionPool;

public class JdbcCellSource extends AbstractCellSource {

	private JdbcConnectionPool pool;

	private String url;

	private ConnectionPoolWrapper poolWrapper;

	public JdbcCellSource() {
	}

	@Override
	public List<CellRecord> getCellList(String parentId, String typeId) {

		List<CellRecord> rt = new SbSelectCellSql(this.poolWrapper).parentId(parentId).typeId(typeId).execute();

		return rt;
	}

	@Override
	public String save(final CellRecord ao) {
		String id = new CellInsertOperation(this.poolWrapper).cell(ao).execute();
		ao.setId(id);
		return id;

	}

	public void configure(Configuration cfg) {
		url = cfg.getString("url", true);

	}

	@Override
	public void open() {

		// Properties pts = new
		// GetPropertiesOperation(this.poolWrapper).execute();
		// String version = pts.getProperty("version");
		pool = JdbcConnectionPool.create(this.url, "sa", "sa");
		this.poolWrapper = new ConnectionPoolWrapper(this.pool);
		new InitialDBOperation(this.poolWrapper).execute();

	}

	@Override
	public void close() {
		pool.dispose();
	}

	@Override
	public void clear() {
		new ClearDBOperation(this.poolWrapper).execute();
	}

	@Override
	public CellRecord getCell(String cellId) {

		return null;
	}

	@Override
	public void saveRelation(String id1, char typeId, String id2) {
		new RelationInsertOperation(this.poolWrapper).relation(id1, typeId, id2).execute();
	}

	@Override
	public void destroy() {
		new DropDBOperation(this.poolWrapper).execute();
	}

}
