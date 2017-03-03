package com.common;

import java.sql.Connection;
import java.sql.SQLException;

import com.common.dbsch.SysConnection;

public class BasicDataSource extends org.apache.commons.dbcp.BasicDataSource {
	
	@Override
	public Connection getConnection() throws SQLException {
		return SysConnection.getInstance(super.getConnection(), new Throwable());
	}
	@Override
	public Connection getConnection(String user, String pass) throws SQLException {
		return SysConnection.getInstance(super.getConnection(user, pass), new Throwable());
	}
}
