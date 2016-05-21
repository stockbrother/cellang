package com.sbrother.sbook.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class H2Tmp {
	public static void main(String[] a) throws Exception {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:mem:db1", "sa", "");
		// add application code here
		String sql = "create table tmp1(id varchar2,name varchar2);";
		PreparedStatement ps = conn.prepareStatement(sql);
		try {
			ps.executeUpdate();
		} finally {
			ps.close();
		}
		sql = "select * from tmp1";
		ps = conn.prepareStatement(sql);
		try{
			ResultSet rs = ps.executeQuery();
			try{
				rs.next();
			}finally{
				rs.close();
			}
		}finally{
			ps.close();
		}
		conn.close();
	}

}
