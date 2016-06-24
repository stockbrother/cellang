/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

/**
 * @author wu
 * 
 */
public class AccountEntity extends EntityObject {

	public static String tableName = "account";

	private String email;

	private String password;

	private String nick;

	private String type;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void fillInsert(InsertRowOperation insert) {
		insert.addValue("email", email);
		insert.addValue("password", password);
		insert.addValue("nick", nick);
	}

	public void extractFrom(ResultSet rs) throws SQLException {
		this.email = rs.getString("email");
		this.nick = rs.getString("nick");
		this.password = rs.getString("password");
	}

	public static void fillCreate(CreateTableOperation cto) {
		cto.addColumn("email", String.class);
		cto.addColumn("password", String.class);
		cto.addColumn("nick", String.class);

	}

}
