package es.conectores.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import es.conectores.controlador.interfaz.DaoCoche;
import es.conectores.modelo.Coche;

public class DaoCocheMySql implements DaoCoche {

	private Connection conexion;
	
	//ABRIR CONEXION
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/CONECTORES";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//CERRAR CONEXION
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	//AÑADIR COCHE
	@Override
	public boolean añadirCoche(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean alta = true;
		
		String query = "insert into COCHES (MATRICULA,MARCA)"
				+ " values(?,?)";
		try {
			
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				alta = false;
		} catch (SQLException e) {
			System.out.println("Error al añadir: " + c);
			alta = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return alta;
	}

	//BUSCAR COCHE
	@Override
	public Coche buscarCoche(int id) {
		if(!abrirConexion()){
			return null;
		}		
		Coche coche = null;
		
		String query = "select ID,MATRICULA,MARCA from COCHES "
				+ "where ID = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println("Error al obtener el "
					+ "coche con id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		return coche;
	}

	//MODIFICAR COCHE
	@Override
	public boolean modificarCoche(Coche c) {
		if(!abrirConexion()){
			return false;
		}
		boolean flag = true;
		String query = "update COCHES set MATRICULA=?, MARCA=? "
				+ "WHERE ID=?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setString(1, c.getMatricula());
			ps.setString(2, c.getMarca());
			ps.setInt(3, c.getId());
			
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				flag = false;
		} catch (SQLException e) {
			System.out.println("Error al modificar el "
					+ " coche " + c);
			flag = false;
			e.printStackTrace();
		} finally{
			cerrarConexion();
		}
		
		return flag;
	}

	//LISTAR COCHES
	@Override
	public List<Coche> list() {
		if(!abrirConexion()){
			return null;
		}		
		List<Coche> lista = new ArrayList<>();
		
		String query = "SELECT ID,MATRICULA,MARCA FROM COCHES";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Coche coche = new Coche();
				coche.setId(rs.getInt(1));
				coche.setMatricula(rs.getString(2));
				coche.setMarca(rs.getString(3));
				
				
				lista.add(coche);
			}
		} catch (SQLException e) {
			System.out.println("Error al listar las "
					+ "personas");
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		
		return lista;
	}


	//BORRAR COCHE
	@Override
	public boolean borrarCoche(int id) {
		if(!abrirConexion()){
			return false;
		}
		
		boolean flag = true;
		String query = "DELETE FROM COCHES WHERE ID = ?";
		try {
			PreparedStatement ps = conexion.prepareStatement(query);
			
			ps.setInt(1, id);
			
			int numeroFilasAfectadas = ps.executeUpdate();
			if(numeroFilasAfectadas == 0)
				flag = false;
		} catch (SQLException e) {
			flag = false;
			System.out.println("Error al borrar"
					+ " el id " + id);
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
		return flag; 
	}
	
	

}
