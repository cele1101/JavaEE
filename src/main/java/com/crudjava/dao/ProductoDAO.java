package com.crudjava.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crudjava.conexion.Conexion;
import com.crudjava.model.Producto;


public class ProductoDAO {

	private Connection connection;
	private boolean estadoOperacion;
	private PreparedStatement statement;

	public boolean guardar(Producto producto) throws SQLException {
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql="insert into productos(id,nombre,cantidad, precio, fecha_crear) values (?,?,?,?,?)";
			statement=connection.prepareStatement(sql);
			
			statement.setString(1,null );
			statement.setString(2,producto.getNombre() );
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFechaCrear());
			
			estadoOperacion=statement.executeUpdate()>0;
			connection.commit(); //se hace comit y se guarda los datos			 
			statement.close();
			connection.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
	}

	public boolean editar(Producto producto) throws SQLException {
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql="update productos set nombre =?, cantidad=?, precio=?, fecha_actualizar=? where id=?";
			statement=connection.prepareStatement(sql);
			
			
			statement.setString(1,producto.getNombre() );
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFechaActualizar());
			statement.setInt(5,producto.getId());
			
			estadoOperacion=statement.executeUpdate()>0;
			connection.commit(); //se hace comit y se guarda los datos			 
			statement.close();
			connection.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;

	}

	public boolean eliminar(int idProducto) throws SQLException {
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql="delete from productos where id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setInt(1,idProducto);
			
			estadoOperacion=statement.executeUpdate()>0;
			connection.commit(); //se hace comit y se guarda los datos			 
			statement.close();
			connection.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
			connection.rollback();
			e.printStackTrace();
		}
		
		return estadoOperacion;
	}

	public List<Producto> obtenerArticulos() throws SQLException {
		
		ResultSet resulSet=null;
		List<Producto> listaProductos=new ArrayList<Producto>();
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();

		try {
			
			sql="select * from productos";
			statement=connection.prepareStatement(sql);
			resulSet=statement.executeQuery(sql);
			
			
			while(resulSet.next()) {
				Producto p=new Producto();
				p.setId(resulSet.getInt(1));
				p.setNombre(resulSet.getString(2));
				p.setCantidad(resulSet.getDouble(3));
				p.setPrecio(resulSet.getDouble(4));
				p.setFechaCrear(resulSet.getDate(5));
				p.setFechaActualizar(resulSet.getDate(6));
				listaProductos.add(p);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return listaProductos;
	}
	
	public Producto obtenerArticulo(int idProducto) throws SQLException {
		ResultSet resulSet=null;
		Producto p=new Producto();
		
		
		String sql=null;
		estadoOperacion=false;
		connection=obtenerConexion();

		try {
			
			sql="select * from productos where id=?";
			statement=connection.prepareStatement(sql);
			statement.setInt(1,idProducto);
			
			resulSet=statement.executeQuery();
			
			if(resulSet.next()) {

				p.setId(resulSet.getInt(1));
				p.setNombre(resulSet.getString(2));
				p.setCantidad(resulSet.getDouble(3));
				p.setPrecio(resulSet.getDouble(4));
				p.setFechaCrear(resulSet.getDate(5));
				p.setFechaActualizar(resulSet.getDate(6));
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return p;
	}
	
	//accede a la conexion 
	private Connection obtenerConexion() throws SQLException {
		
		return  Conexion.getConnection();
	}
}
