package es.conectores.controlador;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*Creamos un controlador para comprobar la BBDD creada,
 * y si no la encuentra creara una
 */
public class DaoBaseDatos {
	

	//CREAR BBDD
	private Connection conexion;
	/*Creamos una BBDD en el localhost:3306 de nombre ""Conectores",
	 * devolvera un "true" si ha podido crear lo BBDD y "false" si no
	 */
	public boolean crearBaseDatos(){
		String url = "jdbc:mysql://localhost:3306/";
		String usuario = "root";
		String password = "";
		try {
			String sql = "CREATE DATABASE CONECTORES";
			conexion = DriverManager.getConnection(url,usuario,password);
			Statement st = conexion.createStatement();
			st.executeUpdate(sql);
			cerrarConexion();	
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	//CONECTAR A BBDD
	/*Intentaremos conectarnos en el localhost 3306, y si ha podido hacerlo devolvera "true",
	 * y si no devolvera "false"
	 */
	public boolean abrirConexion(){
		String url = "jdbc:mysql://localhost:3306/CONECTORES";
		String usuario = "root";
		String password = "";
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
		} catch (SQLException e) {			
			return false;
		}
		return true;
	}
	
	//CERRAR BBDD
	/*Intentaremos cerrar la BBDD y si puede cerrarse devolvera "true",
	 * y si no "false"
	 */
	public boolean cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//CREAR TABLAS PREDEFINIDAS EN LA BBDD
	/**
	 * Creamos las tablas ya diseñadas en la BBDD
	 * Con @param tabla - pasajeros o coches para crear las tablas
	 */
	public void crearTabla(String tabla) {
		
		String pasajeros = "CREATE TABLE PASAJEROS( "
				+ "ID INTEGER PRIMARY KEY AUTO_INCREMENT, "
				+ "NOMBRE VARCHAR(20) NOT NULL, "
				+ "EDAD INTEGER NOT NULL, "
				+ "PESO DOUBLE NOT NULL, "
				+ "COCHE_ID INTEGER, "
				+ "FOREIGN KEY (COCHE_ID) REFERENCES COCHES(ID))";
		String coches = "CREATE TABLE COCHES( "
				+ "ID INTEGER PRIMARY KEY AUTO_INCREMENT, "
				+ "MATRICULA VARCHAR(7) NOT NULL, "
				+ "MARCA VARCHAR(20) NOT NULL)";
		try{
			
			abrirConexion();
			Statement st = conexion.createStatement();
			if(tabla.equals("PASAJEROS")) {
				st.executeUpdate(pasajeros);
			}else if(tabla.equals("COCHES")) {
				st.executeUpdate(coches);
			}
			
		}catch (SQLException e) {
			System.out.println("checkTables()" + e.getMessage());
		}
		cerrarConexion();
		
	}
	

	//BUSCAR EN BBDD LAS TABLAS COCHES Y PASAJEROS
	/*Buscamos en la BBDD las tablas de coches y pasjeros,
	y si no las encuentra las creara
	 */
	public void checkTable() {
		
		try {
			abrirConexion();
			String[] tables = {"COCHES","PASAJEROS"};
			DatabaseMetaData metaData = conexion.getMetaData();
			for (int i=0; i<tables.length; i++) {
				ResultSet rs = metaData.getTables(null,null,tables[i],null);
				if(!rs.next()) {
					crearTabla(tables[i]);
				}
			}
			
		}catch (SQLException e) {
			System.out.println("checkTables() =" + e.getMessage());
		}
		cerrarConexion();
	}
	
	
	

}
