package es.conectores.controlador.interfaz;

import java.util.List;

import es.conectores.modelo.Coche;

//CONTROLADOR DE LOS COCHES EN LA BBDD
public interface DaoCoche {

	//AÑADIR COCHE
	/*@param id busca la id del coche
	 *@return coche si lo encuentra, si no
	 null
	 */
	public boolean añadirCoche(Coche c);
	
	//BUSCAR COCHE
	/*@param id del coche para buscar
	 *@return coche si lo encuentra, si no
	 null
	*/
	public Coche buscarCoche(int id);
	

	//MODIFICAR COCHE
	/*buscamos un coche por id y lo modificamos
	 * @param c del coche con la id para modificar
	 * @return true si se ha podido modificar, si no 
	 * false
	 */
	public boolean modificarCoche(Coche c);
	

	//BORRAR COCHE
	/*Buscamos un coche por id y lo borramos
	 * @param id del coche para buscar
	 * @rteturn true si lo ha podido borrar, si no
	 * false
	 */
	public boolean borrarCoche(int id);
	

	//LISTAR COCHES
	/*Devolvera una lista con los coches mediante @retrun lista de coches */
	public List<Coche> list();
}