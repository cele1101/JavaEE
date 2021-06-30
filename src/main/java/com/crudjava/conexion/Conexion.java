package com.crudjava.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class Conexion {
	
private static BasicDataSource dataSource =null;
	
	public static BasicDataSource getDataSource() {
		
		if(dataSource==null) {
			
			dataSource=new BasicDataSource();
			/* ds.setDriverClassName("com.mysql.cj.jdbc.Driver"); */
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			dataSource.setUrl("jdbc:mysql://localhost:3306/crud?verifyServerCertificate=false&useSSL=true");//Definimos el tamaño del pool de conexiones
			dataSource.setInitialSize(50); //50 conexiones iniciales
			dataSource.setMaxIdle(10);
			dataSource.setMinIdle(20);
			dataSource.setMaxWait(5000);
		}
		
		return dataSource;	
	}
	
	public static Connection getConnection() throws SQLException {
		
		return  getDataSource().getConnection();
	}
}
