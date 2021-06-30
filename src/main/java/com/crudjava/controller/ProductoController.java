package com.crudjava.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crudjava.dao.ProductoDAO;
import com.crudjava.model.Producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet("/productos")
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String opcion=request.getParameter("opcion");
		
		if(opcion.equals("crear")) {
			System.out.println("Usted ha presionado la opcion crear");
			RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/crear.jsp");
			requestDispatcher.forward(request, response);
		}
		
		  else  {
			  if(opcion.equals("listar")) {
				  System.out.println("Usted ha presionado la opcion listar");
				  ProductoDAO productoDAO=new ProductoDAO();
				  List<Producto> lista=new ArrayList<Producto>();
				   try {
					   lista=productoDAO.obtenerArticulos();
					   for(Producto producto:lista) {
						   System.out.println(producto);
					   }
					   request.setAttribute("lista", lista);
					   RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/listar.jsp");
						requestDispatcher.forward(request, response);
					   
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				  
		  }
			  else {
				  
				  if(opcion.equals("meditar")) {
					  int id=Integer.parseInt(request.getParameter("id"));
							  System.out.println("Editar id: "+id);
					  ProductoDAO productoDAO=new ProductoDAO();
					  Producto p=new Producto();
					  
					   try {
						   p=productoDAO.obtenerArticulo(id);
						   System.out.println(p);
						   request.setAttribute("producto", p);
						   RequestDispatcher requestDispatcher=request.getRequestDispatcher("/views/editar.jsp");
						   System.out.println("paso");
							requestDispatcher.forward(request, response);
						   
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					  
				  }
				  else if(opcion.equals("eliminar")) {
					  
					  ProductoDAO productoDAO=new ProductoDAO();
					  int id=Integer.parseInt(request.getParameter("id"));
					  
					  try {
						   productoDAO.eliminar(id);
						   System.out.println("Registro eliminado correctamente");
						   RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
							requestDispatcher.forward(request, response);
						   
					} catch (SQLException e) {
						// TODO: handle exception
						e.printStackTrace();
					} 
					  
					  
				  }
			  }
			  
		  
		  }
		 
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String opcion=request.getParameter("opcion");
		Date fechaActual=new Date();
		
		if(opcion.equals("guardar")) {
			
			ProductoDAO productoDAO=new ProductoDAO();
			Producto producto=new Producto();
			producto.setNombre(request.getParameter("nombre"));
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
			
			try {
				productoDAO.guardar(producto);
				System.out.println("El registro se guardo correctamente");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		else if(opcion.equals("editar")) {
			
			ProductoDAO productoDAO=new ProductoDAO();
			Producto producto=new Producto();
			
			producto.setId(Integer.parseInt(request.getParameter("id")));
			producto.setNombre(request.getParameter("nombre"));
			producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
			producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
			producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
			
			try {
				productoDAO.editar(producto);
				System.out.println("El registro se edito correctamente");
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		/* doGet(request, response); */
	}

}
