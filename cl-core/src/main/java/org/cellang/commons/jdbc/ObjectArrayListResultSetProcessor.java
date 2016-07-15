package org.cellang.commons.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectArrayListResultSetProcessor implements ResultSetProcessor<List<Object[]>> {

	@Override
	public List<Object[]> process(ResultSet rs) throws SQLException {
		List<Object[]> rt = new ArrayList<>();
		int cols = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			Object[] row = new Object[cols];
			for (int i = 0; i < cols; i++) {
				row[i] = rs.getObject(i + 1);
			}
			rt.add(row);
		}
		return rt;
	}

}
