package es.conectores.controlador.interfaz;

import java.util.List;

import es.conectores.modelo.Pasajero;

//CONTROLADOR DE LOS PASAJEROS EN LA BBDD
public interface DaoPasajero {


	//ALTA PASAJERO
	public boolean alta(Pasajero p);
	
	//BAJA PASAJERO
	public boolean baja(int id);
	
	//BUSCAR PASAJERO POR ID
	public Pasajero obtener(int id);
	
	//LSITAR PASAJEROS
	public List<Pasajero> listar();
	
	//ASIGNAR PASAJERO A UN COCHE
	/*@param idpasajero es la id del apsajero que vamos a asignar
	 * @ param idcoche es la id del coche que va a recibir al pasajero
	 * @return true si puede asignarlo y false si no
	 */
	public boolean asignarCoche(int idPasajero, int idCoche);
	

	//DESIGNAR UN PASAJERO EN EL COCHE QUE PERTENECE
	/*@param idpasajero del que queremos asignar
	 * @return true si lo designa correctamente y false si no
	 */
	public boolean desasignarCoche(int idPasajero);
	
	//LISTAR PASAJEROS
	/*@param idcoche del coche que queremos listar los pasajeros
	 * @return lista de pasajeros
	 */
	public List<Pasajero> listarPasajeros(int idCoche);
	
	//CONSULTA BBDD DE LOS COCHES CON PASAJEROS
	public void joinList();
	
}
