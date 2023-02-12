package es.conectores.vista;

import java.util.Scanner;

import es.conectores.controlador.DaoBaseDatos;
import es.conectores.controlador.DaoCocheMySql;
import es.conectores.controlador.DaoPasajeroMySql;
import es.conectores.controlador.interfaz.DaoCoche;
import es.conectores.controlador.interfaz.DaoPasajero;
import es.conectores.modelo.Coche;
import es.conectores.modelo.Pasajero;


public class Main {

	//DAO PARA EL MENU PRINCIPAL Y EL SUB MENU
	static DaoCoche dc = new DaoCocheMySql();
	static DaoPasajero dp = new DaoPasajeroMySql();

	public static void main(String[] args) {

		//Preparar la BBDD
		DaoBaseDatos dao = new DaoBaseDatos();

		if (!dao.abrirConexion()) {
			dao.crearBaseDatos();
			dao.checkTable();
		} else {
			System.out.println("BBDD conectada");
			dao.checkTable();
		}

		//Abrir el menu principal
		menuPrincipal();

	}

	public static void menuPrincipal() {
		try (Scanner sc = new Scanner(System.in)) {

			boolean continuar = true;
			int num;

			System.out.println("\nGestion de coches\n");
			do {
				System.out.println("\nElije un numero del menu:\n" + 
				"1. Añadir coche\n"+ 
				"2. Borrar coche\n" + 
				"3. Buscar coche por ID\n" + 
				"4. Modificar coche por ID\n"+ 
				"5. Listar coches\n" + 
				"6. Gestion de pasajeros\n" + 
				"7. Salir");
				num = Integer.parseInt(sc.nextLine());
				Coche coche;
				switch (num) {

				//AÑADIR COCHE
				case 1:
					coche = new Coche();
					System.out.println("Introduzca Matricula");
					coche.setMatricula(sc.nextLine());
					System.out.println("Introduzca Marca");
					coche.setMarca(sc.nextLine());
					if (dc.añadirCoche(coche)) {
						System.out.println("Coche añadido\n");
						}

					break;

				//BORRAR COCHE
				case 2:
					System.out.println("Introduzca ID del coche que desea borrar");
					if (dc.borrarCoche(Integer.parseInt(sc.nextLine()))) {
						System.out.println("Coche borrado\n");
						}
					break;

				//BUSCAR COCHE
				case 3:
					System.out.println("Introduzca ID del coche que desea buscar");
					System.out.println(dc.buscarCoche(Integer.parseInt(sc.nextLine())));

					break;

				//MODIFICAR COCHE
				case 4:
					coche = new Coche();
					System.out.println("Introduce el ID del coche a modificar");
					coche.setId(Integer.parseInt(sc.nextLine()));
					System.out.println("Introduce la Matricula");
					coche.setMatricula(sc.nextLine());
					System.out.println("Introduce Marca");
					coche.setMarca(sc.nextLine());
					if (dc.modificarCoche(coche)) {
						System.out.println("Coche modificado");
						}
					break;

				//LISTAR COCHES
				case 5:
					System.out.println("Lista de coches : ");
					System.out.println(dc.list());
					break;

				//GESTION DE PASAJEROS
				case 6:
					subMenu(); //Abre sub menu de gestion de pasajeros
					break;

				//SALIR
				case 7:
					System.out.println("Saliendo");
					System.exit(0);
				default:
					System.out.println("Introduzca un numero del menu");
				}

			} while (continuar);

		} catch (Exception e) {
		}
	}

	public static void subMenu() {

		try (Scanner sc2 = new Scanner(System.in)) {
			boolean flag = true;
			int num2, idPasaj, idCoche;

			Pasajero pasajero;
			System.out.println("\nGestion de pasajeros\n");
			do {
				System.out.println("\nElije un numero del menu:\n" + 
				"1. Añadir persona\n"+ 
				"2. Borrar persona\n" + 
				"3. Buscar persona por ID\n"+ 
				"4. Listar personas\n" + 
				"5. Añadir pasajero a un coche\n"+ 
				"6. Borrar pasajero de un coche\n" + 
				"7. Listar pasajeros de un coche\n"+ 
				"8. Listar Coches con sus pasajeros\n" + 
				"9. Volver al menu anterior");
				num2 = Integer.parseInt(sc2.nextLine());

				switch (num2) {

				//AÑADIR PASAJERO
				case 1:
					pasajero = new Pasajero();
					System.out.println("Introduzca nombre");
					pasajero.setNombre(sc2.nextLine());
					System.out.println("Introduzca Edad");
					pasajero.setEdad(Integer.parseInt(sc2.nextLine()));
					System.out.println("Introduzca Peso");
					pasajero.setPeso(Double.parseDouble(sc2.nextLine()));
					if (dp.alta(pasajero)) {
						System.out.println("Persona añadida");
						}

					break;

				//BORRAR PASAJERO
				case 2:
					System.out.println("Introduzca ID de la persona que desea borrar");
					if (dp.baja(Integer.parseInt(sc2.nextLine()))) {
						System.out.println("Persona borrada");
						}
					break;

				//BUSCAR PASAJERO
				case 3:
					System.out.println("Introduzca ID de la persona");
					System.out.println(dp.obtener(Integer.parseInt(sc2.nextLine())));
					break;

				//LISTAR PASAJEROS
				case 4:
					System.out.println("Listado de personas:");
					System.out.println(dp.listar());
					break;

				//AÑADIR PASAJERO AL COCHE
				case 5:
					System.out.println(dp.listar());
					System.out.println("Introduzca el Id del pasajero que quiere asignar");
					idPasaj = Integer.parseInt(sc2.nextLine());
					System.out.println("Los coches disponibles son: ");
					System.out.println(dc.list());
					System.out.println("Introduzca el id del coche que desea");
					idCoche = Integer.parseInt(sc2.nextLine());
					if (dp.asignarCoche(idPasaj, idCoche)) {
						System.out.println("Pasajero añadido al coche con exito");
						}
					break;

				//BORRAR PASAJERO DE UN COCHE
				case 6:

					dp.joinList();
					System.out.println("Introduzca el Id del pasajero que quiere borrar del coche");
					idPasaj = Integer.parseInt(sc2.nextLine());

					if (dp.desasignarCoche(idPasaj)) {
						System.out.println("Pasajero borrado");
						}
					break;

				//LISTAR PASAJEROS DE UN COCHE
				case 7:
					System.out.println("Introduzca el id del coche que desea ver sus pasajeros");
					idCoche = Integer.parseInt(sc2.nextLine());
					System.out.println(dp.listarPasajeros(idCoche));

					break;

				//LISTAR COCHES CON PASAJEROS
				case 8:
					dp.joinList();
					break;

				//VOLVER AL MENU ANTERIOR
				case 9:
					System.out.println("Volviendo al menu anterior");
					menuPrincipal();
					break;
				default:
					System.out.println("Introduzca un numero del menu");
				}

			}while (flag);
			
		} catch (Exception e) {

		}

	}
}
