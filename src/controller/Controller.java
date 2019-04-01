package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.vo.*;
import model.data_structures.*;
import model.logic.ManejoFechaHora;
import model.logic.MovingViolationsManager;
import view.MovingViolationsManagerView;

public class Controller {

	// Componente vista (consola)
	private MovingViolationsManagerView view;
	
	// Componente modelo (logica de la aplicacion)
	private MovingViolationsManager model;
	public static final String rutaEnero = "./data/Moving_Violations_Issued_in_January_2018-2.csv";

	/**
	 * Ruta de archivo CSV Febrero.
	 */
	public static final String rutaFebrero = "./data/Moving_Violations_Issued_in_February_2018.csv";

	/**
	 * Ruta de archivo CSV Marzo.
	 */
	public static final String rutaMarzo = "./data/Moving_Violations_Issued_in_March_2018.csv";

	/**
	 * Ruta de archivo CSV Abril.
	 */
	public static final String rutaAbril = "./data/Moving_Violations_Issued_in_April_2018.csv";

	/**
	 * Ruta de archivo CSV Mayo.
	 */
	public static final String rutaMayo = "./data/Moving_Violations_Issued_in_May_2018.csv";
	/**
	 * Ruta de archivo CSV Junio.
	 */
	public static final String rutaJunio = "./data/Moving_Violations_Issued_in_June_2018.csv";
	/**
	 * Ruta de archivo CSV Julio.
	 */
	public static final String rutaJulio = "./data/Moving_Violations_Issued_in_July_2018.csv";
	/**
	 * Ruta de archivo CSV Agosto.
	 */
	public static final String rutaAgosto = "./data/Moving_Violations_Issued_in_August_2018.csv";
	/**
	 * Ruta de archivo CSV Septiembre.
	 */
	public static final String rutaSeptiembre = "./data/Moving_Violations_Issued_in_September_2018.csv";
	/**
	 * Ruta de archivo CSV Octubre.
	 */
	public static final String rutaOctubre = "./data/Moving_Violations_Issued_in_October_2018.csv";
	/**
	 * Ruta de archivo CSV Noviembre.
	 */
	public static final String rutaNoviembre = "./data/Moving_Violations_Issued_in_November_2018.csv";
	/**
	 * Ruta de archivo CSV Diciembre.
	 */
	public static final String rutaDiciembre = "./data/Moving_Violations_Issued_in_December_2018.csv";
	/**
	 * Metodo constructor
	 */
	public Controller()
	{
		view = new MovingViolationsManagerView();
		model = new MovingViolationsManager();
	}
	
	/**
	 * Metodo encargado de ejecutar los  requerimientos segun la opcion indicada por el usuario
	 */
	public void run(){

		long startTime;
		long endTime;
		long duration;

		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		Controller controller = new Controller();

		while(!fin){
			view.printMenu();

			int option = sc.nextInt();

			switch(option){

			case 0:
				view.printMessage("Ingrese semestre a cargar (1 o 2)");
				int semestre = sc.nextInt();
				EstadisticasCargaInfracciones resumenCarga = model.loadMovingViolations(semestre);

				//TODO Mostrar resultado de tipo EstadisticasCargaInfracciones con: 
				//     total de infracciones cargadas, numero de infracciones cargadas por mes y zona Minimax (Xmin, Ymin) y (Xmax, Ymax)
				//view.printResumenLoadMovingViolations( ... );
				break;

			case 1:
				view.printMessage("1A. Consultar las N franjas horarias con mas infracciones que desea ver. Ingresar valor de N: ");
				int numeroFranjas = sc.nextInt();

				//TODO Completar para la invocación del metodo 1A
				//model.rankingNFranjas(int N)
				
				//TODO Mostrar resultado de tipo Cola con N InfraccionesFranjaHoraria
				//view.printReq1A( ...);
				break;

			case 2:
				view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 1234,56): ");
				double xcoord = sc.nextDouble();
				view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 5678,23): ");
				double ycoord = sc.nextDouble();

				//TODO Completar para la invocación del metodo 2A
				//model.consultarPorLocalizacionHash(double xCoord, double yCoord)

				//TODO Mostrar resultado de tipo InfraccionesLocalizacion 
				//view.printReq2A( ... )
				break;

			case 3:
				view.printMessage("Ingrese la fecha inicial del rango. Formato año-mes-dia (ej. 2008-06-21)");
				String fechaInicialStr = sc.next();
				LocalDate fechaInicial = ManejoFechaHora.convertirFecha_LD( fechaInicialStr );

				view.printMessage("Ingrese la fecha final del rango. Formato año-mes-dia (ej. 2008-06-30)");
				String fechaFinalStr = sc.next();
				LocalDate fechaFinal = ManejoFechaHora.convertirFecha_LD( fechaFinalStr );

				//TODO Completar para la invocacion del metodo 3A
				//model.consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)

				//TODO Mostrar resultado de tipo Cola de InfraccionesFecha
				//view.printReq3A( ... )
				break;


			case 4:
				view.printMessage("1B. Consultar los N Tipos con mas infracciones. Ingrese el valor de N: ");
				int numeroTipos = sc.nextInt();

				//TODO Completar para la invocación del metodo 1B				
				//model.rankingNViolationCodes(int N)
				
				//TODO Mostrar resultado de tipo Cola con N InfraccionesViolationCode
				//view.printReq1B( ... )
				break;

			case 5:						
				view.printMessage("Ingrese la coordenada en X de la localizacion geografica (Ej. 1234,56): ");
				xcoord = sc.nextDouble();
				view.printMessage("Ingrese la coordenada en Y de la localizacion geografica (Ej. 5678,23): ");
				ycoord = sc.nextDouble();

				//TODO Completar para la invocación del metodo 2B
				//model.consultarPorLocalizacionArbol(double xCoord, double yCoord)

				//TODO Mostrar resultado de tipo InfraccionesLocalizacion 
				//view.printReq2B( ... )
				break;

			case 6:
				view.printMessage("Ingrese la cantidad minima de dinero que deben acumular las infracciones en sus rangos de fecha  (Ej. 1234,56)");
				double cantidadMinima = sc.nextDouble();

				view.printMessage("Ingrese la cantidad maxima de dinero que deben acumular las infracciones en sus rangos de fecha (Ej. 5678,23)");
				double cantidadMaxima = sc.nextDouble();

				//TODO Completar para la invocación del metodo 3B
				//model.consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)

				//TODO Mostrar resultado de tipo Cola con InfraccionesFechaHora 
				//view.printReq3B( ... )
				break;

			case 7:
				view.printMessage("1C. Consultar las infracciones con Address_Id. Ingresar el valor de Address_Id: ");
				int addressID = sc.nextInt();

				startTime = System.currentTimeMillis();
				//TODO Completar para la invocación del metodo 1C
				//model.consultarPorAddressId(int addressID)

				endTime = System.currentTimeMillis();

				duration = endTime - startTime;
				view.printMessage("Tiempo requerimiento 1C: " + duration + " milisegundos");

				//TODO Mostrar resultado de tipo InfraccionesLocalizacion 	
				//view.printReq1C( ... )
				break;

			case 8:
				view.printMessage("Ingrese la hora inicial del rango. Formato HH:MM:SS (ej. 09:30:00)");
				String horaInicialStr = sc.next();
				LocalTime horaInicial = ManejoFechaHora.convertirHora_LT(horaInicialStr);

				view.printMessage("Ingrese la hora final del rango. Formato HH:MM:SS (ej. 16:00:00)");
				String horaFinalStr = sc.next();
				LocalTime horaFinal = ManejoFechaHora.convertirHora_LT(horaFinalStr);

				startTime = System.currentTimeMillis();
				//TODO Completar para la invocacion del metodo 2C
				//model.consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)

				endTime = System.currentTimeMillis();

				duration = endTime - startTime;
				view.printMessage("Tiempo requerimiento 2C: " + duration + " milisegundos");
				//TODO Mostrar resultado de tipo InfraccionesFranjaHorarioViolationCode
				//view.printReq2C( ... )
				break;

			case 9:
				view.printMessage("Consultar las N localizaciones geograficas con mas infracciones. Ingrese el valor de N: ");
				int numeroLocalizaciones = sc.nextInt();

				startTime = System.currentTimeMillis();
				//TODO Completar para la invocación del metodo 3C
				//model.rankingNLocalizaciones(int N)

				endTime = System.currentTimeMillis();

				duration = endTime - startTime;
				view.printMessage("Tiempo requerimiento 3C: " + duration + " milisegundos");
				//TODO Mostrar resultado de tipo Cola con InfraccionesLocalizacion
				//view.printReq3C( ... )
				break;

			case 10:

				System.out.println("Grafica ASCII con la informacion de las infracciones por ViolationCode");

				startTime = System.currentTimeMillis();
				//TODO Completar para la invocacion del metodo 4C
				//model.ordenarCodigosPorNumeroInfracciones()

				//TODO Mostrar grafica a partir del resultado del metodo anterior
				//view.printReq4C( ... )
				endTime = System.currentTimeMillis();

				duration = endTime - startTime;
				view.printMessage("Tiempo requerimiento 4C: " + duration + " milisegundos");
				break;

			case 11:	
				fin = true;
				sc.close();
				break;
			}
		}
	}


	private MovingViolationsManagerView view;
	private ArregloDinamico<VOMovingViolations> arreglo;


	public void loadMovingViolations(int semestre) {
		try {
			double xmin=0;
			double xmax=0;
			double ymin=0;
			double ymax=0;
			if(semestre ==1){
				CSVReader lectorEnero = new CSVReader(new FileReader(rutaEnero));
				String[] lineaEnero = lectorEnero.readNext();
				lineaEnero=lectorEnero.readNext();
				int enero=0;
				xmin=Double.parseDouble(lineaEnero[5]);
				xmax=Double.parseDouble(lineaEnero[5]);
				ymin=Double.parseDouble(lineaEnero[6]);
				ymax=Double.parseDouble(lineaEnero[6]);
				while ((lineaEnero = lectorEnero.readNext()) != null) {
					String obID = lineaEnero[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaEnero[5]);
					double y=Double.parseDouble(lineaEnero[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaEnero[3];
					String streetSegID = lineaEnero[4];
					String fine = lineaEnero[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaEnero[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaEnero[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaEnero[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaEnero[12];
					String issueDate = lineaEnero[13];
					String violationCode = lineaEnero[14];
					String violationDesc = lineaEnero[15];
					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					enero++;}
				view.printMessage("Datos de Enero:"+enero);
				lectorEnero.close();

				CSVReader lectorFebrero = new CSVReader(new FileReader(rutaFebrero));
				String[] lineaFebrero = lectorFebrero.readNext();
				int f=0;
				while ((lineaFebrero = lectorFebrero.readNext()) != null) {
					String obID = lineaFebrero[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaFebrero[5]);
					double y=Double.parseDouble(lineaFebrero[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaFebrero[3];
					String streetSegID = lineaFebrero[4];
					String fine = lineaFebrero[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaFebrero[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaFebrero[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaFebrero[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaFebrero[12];
					String issueDate = lineaFebrero[13];
					String violationCode = lineaFebrero[14];
					String violationDesc = lineaFebrero[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					f++;}
				view.printMessage("Datos de febrero:"+f);
				lectorFebrero.close();

				CSVReader lectorMarzo = new CSVReader(new FileReader(rutaMarzo));
				String[] lineaMarzo = lectorMarzo.readNext();
				int m=0;
				while ((lineaMarzo = lectorMarzo.readNext()) != null) {
					String obID = lineaMarzo[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaMarzo[5]);
					double y=Double.parseDouble(lineaMarzo[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaMarzo[3];
					String streetSegID = lineaMarzo[4];
					String fine = lineaMarzo[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaMarzo[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaMarzo[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaMarzo[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaMarzo[12];
					String issueDate = lineaMarzo[13];
					String violationCode = lineaMarzo[14];
					String violationDesc = lineaMarzo[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					m++;}
				view.printMessage("Datos de Marzo:"+m);
				lectorMarzo.close();

				CSVReader lectorAbril = new CSVReader(new FileReader(rutaAbril));
				String[] lineaAbril = lectorAbril.readNext();
				int a=0;
				while ((lineaAbril = lectorAbril.readNext()) != null) {
					String obID = lineaAbril[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaAbril[5]);
					double y=Double.parseDouble(lineaAbril[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaAbril[3];
					String streetSegID = lineaAbril[4];
					String fine = lineaAbril[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaAbril[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaAbril[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaAbril[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaAbril[12];
					String issueDate = lineaAbril[13];
					String violationCode = lineaAbril[14];
					String violationDesc = lineaAbril[15];
					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));				
					a++;}
				view.printMessage("Datos de Abril:"+a);
				lectorAbril.close();

				CSVReader lectorMayo = new CSVReader(new FileReader(rutaMayo));
				String[] lineaMayo = lectorMayo.readNext();
				int mayo=0;
				while ((lineaMayo = lectorMayo.readNext()) != null) {
					String obID = lineaMayo[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaMayo[5]);
					double y=Double.parseDouble(lineaMayo[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaMayo[3];
					String streetSegID = lineaMayo[4];
					String fine = lineaMayo[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaMayo[9];
					int totalPaid =(int) Double.parseDouble(total);
					String p1 = lineaMayo[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaMayo[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaMayo[12];
					String issueDate = lineaMayo[13];
					String violationCode = lineaMayo[14];
					String violationDesc = lineaMayo[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					mayo++;}
				view.printMessage("Datos de Mayo:"+mayo);
				lectorMayo.close();

				CSVReader lectorJunio = new CSVReader(new FileReader(rutaJunio));
				String[] lineaJunio = lectorJunio.readNext();
				int j=0;
				while ((lineaJunio = lectorJunio.readNext()) != null) {
					String obID = lineaJunio[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaJunio[5]);
					double y=Double.parseDouble(lineaJunio[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaJunio[3];
					String streetSegID = lineaJunio[4];
					String fine = lineaJunio[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaJunio[9];
					int totalPaid =(int) Double.parseDouble(total);
					String p1 = lineaJunio[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaJunio[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaJunio[12];
					String issueDate = lineaJunio[13];
					String violationCode = lineaJunio[14];
					String violationDesc = lineaJunio[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					j++;}
				view.printMessage("Datos de Junio:"+j);
				lectorJunio.close();
			}
			else
			{
				CSVReader lectorJulio = new CSVReader(new FileReader(rutaJulio));
				String[] lineaJulio = lectorJulio.readNext();
				lineaJulio = lectorJulio.readNext();
				int ju=0;
				xmin = Double.parseDouble(lineaJulio[5]);
				xmax =  Double.parseDouble(lineaJulio[5]);
				ymin = Double.parseDouble(lineaJulio[6]);
				ymax = Double.parseDouble(lineaJulio[6]);
				
				while ((lineaJulio = lectorJulio.readNext()) != null) {
					String obID = lineaJulio[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaJulio[5]);
					double y=Double.parseDouble(lineaJulio[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaJulio[3];
					String streetSegID = lineaJulio[4];
					String fine = lineaJulio[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaJulio[9];
					int totalPaid =(int) Double.parseDouble(total);
					String p1 = lineaJulio[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaJulio[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaJulio[12];
					String issueDate = lineaJulio[13];
					String violationCode = lineaJulio[14];
					String violationDesc = lineaJulio[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					ju++;}
				view.printMessage("Datos de Julio:"+ju);
				lectorJulio.close();

				CSVReader lectorAgosto = new CSVReader(new FileReader(rutaAgosto));
				String[] lineaAgosto = lectorAgosto.readNext();
				int ag=0;
				while ((lineaAgosto = lectorAgosto.readNext()) != null) {
					String obID = lineaAgosto[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaAgosto[5]);
					double y=Double.parseDouble(lineaAgosto[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaAgosto[3];
					String streetSegID = lineaAgosto[4];
					String fine = lineaAgosto[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaAgosto[9];
					int totalPaid =(int) Double.parseDouble(total);
					String p1 = lineaAgosto[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaAgosto[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaAgosto[12];
					String issueDate = lineaAgosto[13];
					String violationCode = lineaAgosto[14];
					String violationDesc = lineaAgosto[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					ag++;}
				view.printMessage("Datos de Agosto:"+ag);
				lectorAgosto.close();

				CSVReader lectorSeptiembre = new CSVReader(new FileReader(rutaSeptiembre));
				String[] lineaSeptiembre = lectorSeptiembre.readNext();
				int s=0;
				while ((lineaSeptiembre = lectorSeptiembre.readNext()) != null) {
					String obID = lineaSeptiembre[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaSeptiembre[5]);
					double y=Double.parseDouble(lineaSeptiembre[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaSeptiembre[3];
					String streetSegID = lineaSeptiembre[4];
					String fine = lineaSeptiembre[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaSeptiembre[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaSeptiembre[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaSeptiembre[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaSeptiembre[12];
					String issueDate = lineaSeptiembre[13];
					String violationCode = lineaSeptiembre[14];
					String violationDesc = lineaSeptiembre[15];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					s++;}
				view.printMessage("Datos de Septiembre:"+s);
				lectorSeptiembre.close();

				CSVReader lectorOctubre = new CSVReader(new FileReader(rutaOctubre));
				String[] lineaOctubre = lectorOctubre.readNext();
				int o=0;
				while ((lineaOctubre = lectorOctubre.readNext()) != null) {
					String obID = lineaOctubre[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaOctubre[5]);
					double y=Double.parseDouble(lineaOctubre[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaOctubre[3];
					String streetSegID = lineaOctubre[4];
					String fine = lineaOctubre[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaOctubre[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaOctubre[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaOctubre[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaOctubre[12];
					String issueDate = lineaOctubre[14];
					String violationCode = lineaOctubre[15];
					String violationDesc = lineaOctubre[16];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					o++;}
				view.printMessage("Datos de Octubre:"+o);
				lectorOctubre.close();

				CSVReader lectorNoviembre = new CSVReader(new FileReader(rutaNoviembre));
				String[] lineaNoviembre = lectorNoviembre.readNext();
				int n=0;
				while ((lineaNoviembre = lectorNoviembre.readNext()) != null) {
					String obID = lineaNoviembre[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaNoviembre[5]);
					double y=Double.parseDouble(lineaNoviembre[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaNoviembre[3];
					String streetSegID = lineaNoviembre[4];
					String fine = lineaNoviembre[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaNoviembre[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaNoviembre[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaNoviembre[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaNoviembre[12];
					String issueDate = lineaNoviembre[14];
					String violationCode = lineaNoviembre[15];
					String violationDesc = lineaNoviembre[16];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					n++;}
				view.printMessage("Datos de Noviembre:"+n);
				lectorNoviembre.close();

				CSVReader lectorDiciembre = new CSVReader(new FileReader(rutaDiciembre));
				String[] lineaDiciembre = lectorDiciembre.readNext();
				int d=0;
				while ((lineaDiciembre = lectorDiciembre.readNext()) != null) {
					String obID = lineaDiciembre[0];
					int objectID = Integer.parseInt(obID);
					double x=Double.parseDouble(lineaDiciembre[5]);
					double y=Double.parseDouble(lineaDiciembre[6]);
					if(x<xmin)
						xmin=x;
					else if(x>xmax)
						xmax=x;
					 if(y<ymin)
						 ymin=y;
					 else if(y>ymax)
						 ymax=y;
					String address = lineaDiciembre[3];
					String streetSegID = lineaDiciembre[4];
					String fine = lineaDiciembre[8];
					int fineAmt = Integer.parseInt(fine);
					String total = lineaDiciembre[9];
					int totalPaid = Integer.parseInt(total);
					String p1 = lineaDiciembre[10];
					int penalty1 = Integer.parseInt(p1);
					String p2 = lineaDiciembre[11];
					int penalty2 = 0;
					if(!p2.equals("")){
						penalty2 = Integer.parseInt(p2);
					}
					else{
						penalty2 = 0;
					}
					String accidentIndicator = lineaDiciembre[12];
					String issueDate = lineaDiciembre[14];
					String violationCode = lineaDiciembre[15];
					String violationDesc = lineaDiciembre[16];

					arreglo.agregar(new VOMovingViolations(objectID, issueDate, violationCode, fineAmt, address, streetSegID,
							totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));

					d++;}
				view.printMessage("Datos de Diciembre:"+d);
				lectorDiciembre.close();
			}
			view.printMessage("Minimo: ( "+xmin+" , "+ymin+") y Maximo: ( "+xmax+" , "+ymax+")");

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
