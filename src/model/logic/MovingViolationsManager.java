package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.opencsv.CSVReader;

import model.data_structures.ArregloDinamico;
import model.data_structures.IQueue;
import model.vo.EstadisticaInfracciones;
import model.vo.EstadisticasCargaInfracciones;
import model.vo.InfraccionesFecha;
import model.vo.InfraccionesFechaHora;
import model.vo.InfraccionesFranjaHoraria;
import model.vo.InfraccionesFranjaHorariaViolationCode;
import model.vo.InfraccionesLocalizacion;
import model.vo.InfraccionesViolationCode;
import model.vo.VOMovingViolations;

public class MovingViolationsManager {

	//TODO Definir atributos necesarios
	public static final String rutaEnero = "./data/Moving_Violations_Issued_in_January_2018.csv";

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

	private ArregloDinamico<VOMovingViolations> arreglo;
	private String[] sem1;
	private String[] sem2;
	/**
	 * Metodo constructor
	 */
	public MovingViolationsManager()
	{
		arreglo = new ArregloDinamico<>(35000);
		sem1 = new String[6];
		sem2 = new String[6];
		for(int i = 0; i<6;i++){
			if(i == 0){
				sem1[i] = rutaEnero;
				sem2[i] = rutaJulio;
			}
			else if(i == 1){
				sem1[i] = rutaFebrero;
				sem2[i] = rutaAgosto;
			}
			else if(i == 2){
				sem1[i] = rutaMarzo;
				sem2[i] = rutaSeptiembre;
			}
			else if(i == 3){
				sem1[i] = rutaAbril;
				sem2[i] = rutaOctubre;
			}
			else if(i == 4){
				sem1[i] = rutaMayo;
				sem2[i] = rutaNoviembre;
			}
			else if(i == 5){
				sem1[i] = rutaJunio;
				sem2[i] = rutaDiciembre;
			}
		}
	}

	/**
	 * Cargar las infracciones de un semestre de 2018
	 * @param numeroSemestre numero del semestre a cargar (1 o 2)
	 * @return objeto con el resultado de la carga de las infracciones
	 */
	public EstadisticasCargaInfracciones loadMovingViolations(int numeroSemestre) {
		double xmin=0;
		double xmax=0;
		double ymin=0;
		double ymax=0;
		int tot = 0;
		int enero= 0;
		int f=0;
		int a =0;
		int m =0;
		int mayo =0;
		int j =0;
		int ju =0;
		int ag = 0;
		int s =0;
		int o =0;
		int n = 0;
		int d = 0;

		int totalNuevo1 =0;
		int totalNuevo2= 0;
		int contMes=0;
		EstadisticasCargaInfracciones estadistica = null;
		try{
			if(numeroSemestre ==1){
				for(int i = 0;i<sem1.length;i++){


					String mes = sem1[i];
					CSVReader lector = new CSVReader(new FileReader(mes));
					String[] linea = lector.readNext();
					linea=lector.readNext();
					xmin=Double.parseDouble(linea[5]);
					xmax=Double.parseDouble(linea[5]);
					ymin=Double.parseDouble(linea[6]);
					ymax=Double.parseDouble(linea[6]);
					while ((linea = lector.readNext()) != null) {
						contMes = 0;
						String obID = linea[0];
						String location = linea[2];
						int objectID = Integer.parseInt(obID);
						double x=Double.parseDouble(linea[5]);
						double y=Double.parseDouble(linea[6]);
						if(x<xmin)
							xmin=x;
						else if(x>xmax)
							xmax=x;
						if(y<ymin)
							ymin=y;
						else if(y>ymax)
							ymax=y;
						String address = linea[3];
						String streetSegID = linea[4];
						String total = linea[9];
						int totalPaid = Integer.parseInt(total);
						String accidentIndicator = linea[12];
						String issueDate = linea[13];
						String violationDesc = linea[15];
						arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));
						totalNuevo1++;
						contMes++;
						if(i == 0){
							contMes = ju;
						}
						else if(i == 1){
							contMes = ag;
						}
						else if(i == 2){
							contMes = s;

						}
						else if(i == 3){
							contMes = o;

						}
						else if(i == 4){
							contMes = n;

						}
						else if(i == 5){
							contMes = d;

						}
					}

					lector.close();
				}
				estadistica = new EstadisticasCargaInfracciones(totalNuevo2, ju, ag, s,o ,n, d, xmin, ymin,xmax,ymax);
			}
			else{
				for(int i = 0;i<sem2.length;i++){
					
					String mes = sem2[i];
					CSVReader lector = new CSVReader(new FileReader(mes));
					String[] linea = lector.readNext();
					linea=lector.readNext();
					xmin=Double.parseDouble(linea[5]);
					xmax=Double.parseDouble(linea[5]);
					ymin=Double.parseDouble(linea[6]);
					ymax=Double.parseDouble(linea[6]);
					while ((linea = lector.readNext()) != null) {
						contMes = 0;
						String obID = linea[0];
						String location = linea[2];
						int objectID = Integer.parseInt(obID);
						double x=Double.parseDouble(linea[5]);
						double y=Double.parseDouble(linea[6]);
						if(x<xmin)
							xmin=x;
						else if(x>xmax)
							xmax=x;
						if(y<ymin)
							ymin=y;
						else if(y>ymax)
							ymax=y;
						String address = linea[3];
						String streetSegID = linea[4];
						String total = linea[9];
						double totalPaid = Double.parseDouble(total);
						String accidentIndicator = linea[12];
						String issueDate = linea[13];
						String violationDesc = linea[15];
						arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));
						totalNuevo2++;
						contMes++;
						if(i == 0){
							contMes = enero;
						}
						else if(i == 1){
							contMes = f;
						}
						else if(i == 2){
							contMes = m;

						}
						else if(i == 3){
							contMes = a;

						}
						else if(i == 4){
							contMes = mayo;

						}
						else if(i == 5){
							contMes = j;

						}
					}

					lector.close();
				}
				estadistica = new EstadisticasCargaInfracciones(totalNuevo1, enero, f, m, a ,mayo, j, xmin, ymin,xmax,ymax);	
			}

		}
		catch (IOException e) {

			e.printStackTrace();
		}
			//			if(numeroSemestre ==1){
			//				CSVReader lectorEnero = new CSVReader(new FileReader(rutaEnero));
			//				String[] lineaEnero = lectorEnero.readNext();
			//				lineaEnero=lectorEnero.readNext();
			//				xmin=Double.parseDouble(lineaEnero[5]);
			//				xmax=Double.parseDouble(lineaEnero[5]);
			//				ymin=Double.parseDouble(lineaEnero[6]);
			//				ymax=Double.parseDouble(lineaEnero[6]);
			//				while ((lineaEnero = lectorEnero.readNext()) != null) {
			//					String obID = lineaEnero[0];
			//					String location = lineaEnero[2];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaEnero[5]);
			//					double y=Double.parseDouble(lineaEnero[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaEnero[3];
			//					String streetSegID = lineaEnero[4];
			//					String total = lineaEnero[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String accidentIndicator = lineaEnero[12];
			//					String issueDate = lineaEnero[13];
			//					String violationDesc = lineaEnero[15];
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));
			//					tot++;
			//					enero++;}
			//				System.out.println("Datos de Enero:"+enero);
			//				lectorEnero.close();
			//
			//				CSVReader lectorFebrero = new CSVReader(new FileReader(rutaFebrero));
			//				String[] lineaFebrero = lectorFebrero.readNext();
			//				while ((lineaFebrero = lectorFebrero.readNext()) != null) {
			//					String obID = lineaFebrero[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaFebrero[5]);
			//					double y=Double.parseDouble(lineaFebrero[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaFebrero[3];
			//					String streetSegID = lineaFebrero[4];
			//					String fine = lineaFebrero[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaFebrero[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaFebrero[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaFebrero[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaFebrero[12];
			//					String issueDate = lineaFebrero[13];
			//					String violationCode = lineaFebrero[14];
			//					String violationDesc = lineaFebrero[15];
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));
			//					tot++;
			//					f++;}
			//				System.out.println("Datos de Enero:"+f);
			//				lectorFebrero.close();
			//
			//				CSVReader lectorMarzo = new CSVReader(new FileReader(rutaMarzo));
			//				String[] lineaMarzo = lectorMarzo.readNext();
			//				while ((lineaMarzo = lectorMarzo.readNext()) != null) {
			//					String obID = lineaMarzo[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaMarzo[5]);
			//					double y=Double.parseDouble(lineaMarzo[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaMarzo[3];
			//					String streetSegID = lineaMarzo[4];
			//					String fine = lineaMarzo[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaMarzo[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaMarzo[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaMarzo[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaMarzo[12];
			//					String issueDate = lineaMarzo[13];
			//					String violationCode = lineaMarzo[14];
			//					String violationDesc = lineaMarzo[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));
			//					tot++;
			//					m++;}
			//				System.out.println("Datos de Enero:"+m);
			//				lectorMarzo.close();
			//
			//				CSVReader lectorAbril = new CSVReader(new FileReader(rutaAbril));
			//				String[] lineaAbril = lectorAbril.readNext();
			//				while ((lineaAbril = lectorAbril.readNext()) != null) {
			//					String obID = lineaAbril[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaAbril[5]);
			//					double y=Double.parseDouble(lineaAbril[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaAbril[3];
			//					String streetSegID = lineaAbril[4];
			//					String fine = lineaAbril[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaAbril[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaAbril[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaAbril[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaAbril[12];
			//					String issueDate = lineaAbril[13];
			//					String violationCode = lineaAbril[14];
			//					String violationDesc = lineaAbril[15];
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));	
			//					tot++;
			//					a++;}
			//				System.out.println("Datos de Enero:"+a);
			//				lectorAbril.close();
			//
			//				CSVReader lectorMayo = new CSVReader(new FileReader(rutaMayo));
			//				String[] lineaMayo = lectorMayo.readNext();
			//				while ((lineaMayo = lectorMayo.readNext()) != null) {
			//					String obID = lineaMayo[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaMayo[5]);
			//					double y=Double.parseDouble(lineaMayo[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaMayo[3];
			//					String streetSegID = lineaMayo[4];
			//					String fine = lineaMayo[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaMayo[9];
			//					int totalPaid =(int) Double.parseDouble(total);
			//					String p1 = lineaMayo[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaMayo[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaMayo[12];
			//					String issueDate = lineaMayo[13];
			//					String violationCode = lineaMayo[14];
			//					String violationDesc = lineaMayo[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					mayo++;}
			//				System.out.println("Datos de Enero:"+mayo);
			//				lectorMayo.close();
			//
			//				CSVReader lectorJunio = new CSVReader(new FileReader(rutaJunio));
			//				String[] lineaJunio = lectorJunio.readNext();
			//				while ((lineaJunio = lectorJunio.readNext()) != null) {
			//					String obID = lineaJunio[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaJunio[5]);
			//					double y=Double.parseDouble(lineaJunio[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaJunio[3];
			//					String streetSegID = lineaJunio[4];
			//					String fine = lineaJunio[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaJunio[9];
			//					int totalPaid =(int) Double.parseDouble(total);
			//					String p1 = lineaJunio[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaJunio[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaJunio[12];
			//					String issueDate = lineaJunio[13];
			//					String violationCode = lineaJunio[14];
			//					String violationDesc = lineaJunio[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					j++;
			//				}
			//				System.out.println("Datos de Enero:"+j);
			//				lectorJunio.close();
			//				estadistica = new EstadisticasCargaInfracciones(tot, enero, f, m, a ,mayo, j, xmin, ymin,xmax,ymax);
			//			}
			//
			//			else
			//			{
			//				CSVReader lectorJulio = new CSVReader(new FileReader(rutaJulio));
			//				String[] lineaJulio = lectorJulio.readNext();
			//				lineaJulio = lectorJulio.readNext();
			//				xmin = Double.parseDouble(lineaJulio[5]);
			//				xmax =  Double.parseDouble(lineaJulio[5]);
			//				ymin = Double.parseDouble(lineaJulio[6]);
			//				ymax = Double.parseDouble(lineaJulio[6]);
			//
			//				while ((lineaJulio = lectorJulio.readNext()) != null) {
			//					String obID = lineaJulio[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaJulio[5]);
			//					double y=Double.parseDouble(lineaJulio[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaJulio[3];
			//					String streetSegID = lineaJulio[4];
			//					String fine = lineaJulio[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaJulio[9];
			//					int totalPaid =(int) Double.parseDouble(total);
			//					String p1 = lineaJulio[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaJulio[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaJulio[12];
			//					String issueDate = lineaJulio[13];
			//					String violationCode = lineaJulio[14];
			//					String violationDesc = lineaJulio[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					ju++;}
			//				System.out.println("Datos de Enero:"+ju);
			//				lectorJulio.close();
			//
			//				CSVReader lectorAgosto = new CSVReader(new FileReader(rutaAgosto));
			//				String[] lineaAgosto = lectorAgosto.readNext();
			//				while ((lineaAgosto = lectorAgosto.readNext()) != null) {
			//					String obID = lineaAgosto[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaAgosto[5]);
			//					double y=Double.parseDouble(lineaAgosto[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaAgosto[3];
			//					String streetSegID = lineaAgosto[4];
			//					String fine = lineaAgosto[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaAgosto[9];
			//					int totalPaid =(int) Double.parseDouble(total);
			//					String p1 = lineaAgosto[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaAgosto[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaAgosto[12];
			//					String issueDate = lineaAgosto[13];
			//					String violationCode = lineaAgosto[14];
			//					String violationDesc = lineaAgosto[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					ag++;}
			//				System.out.println("Datos de Enero:"+ag);
			//				lectorAgosto.close();
			//
			//				CSVReader lectorSeptiembre = new CSVReader(new FileReader(rutaSeptiembre));
			//				String[] lineaSeptiembre = lectorSeptiembre.readNext();
			//				while ((lineaSeptiembre = lectorSeptiembre.readNext()) != null) {
			//					String obID = lineaSeptiembre[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaSeptiembre[5]);
			//					double y=Double.parseDouble(lineaSeptiembre[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaSeptiembre[3];
			//					String streetSegID = lineaSeptiembre[4];
			//					String fine = lineaSeptiembre[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaSeptiembre[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaSeptiembre[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaSeptiembre[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaSeptiembre[12];
			//					String issueDate = lineaSeptiembre[13];
			//					String violationCode = lineaSeptiembre[14];
			//					String violationDesc = lineaSeptiembre[15];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));						totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					s++;}
			//				System.out.println("Datos de Enero:"+s);
			//				lectorSeptiembre.close();
			//
			//				CSVReader lectorOctubre = new CSVReader(new FileReader(rutaOctubre));
			//				String[] lineaOctubre = lectorOctubre.readNext();
			//				while ((lineaOctubre = lectorOctubre.readNext()) != null) {
			//					String obID = lineaOctubre[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaOctubre[5]);
			//					double y=Double.parseDouble(lineaOctubre[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaOctubre[3];
			//					String streetSegID = lineaOctubre[4];
			//					String fine = lineaOctubre[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaOctubre[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaOctubre[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaOctubre[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaOctubre[12];
			//					String issueDate = lineaOctubre[14];
			//					String violationCode = lineaOctubre[15];
			//					String violationDesc = lineaOctubre[16];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));						totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					o++;}
			//				System.out.println("Datos de Enero:"+o);
			//				lectorOctubre.close();
			//
			//				CSVReader lectorNoviembre = new CSVReader(new FileReader(rutaNoviembre));
			//				String[] lineaNoviembre = lectorNoviembre.readNext();
			//				while ((lineaNoviembre = lectorNoviembre.readNext()) != null) {
			//					String obID = lineaNoviembre[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaNoviembre[5]);
			//					double y=Double.parseDouble(lineaNoviembre[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaNoviembre[3];
			//					String streetSegID = lineaNoviembre[4];
			//					String fine = lineaNoviembre[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaNoviembre[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaNoviembre[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaNoviembre[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaNoviembre[12];
			//					String issueDate = lineaNoviembre[14];
			//					String violationCode = lineaNoviembre[15];
			//					String violationDesc = lineaNoviembre[16];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));					totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					n++;}
			//				System.out.println("Datos de Enero:"+n);
			//				lectorNoviembre.close();
			//
			//				CSVReader lectorDiciembre = new CSVReader(new FileReader(rutaDiciembre));
			//				String[] lineaDiciembre = lectorDiciembre.readNext();
			//				while ((lineaDiciembre = lectorDiciembre.readNext()) != null) {
			//					String obID = lineaDiciembre[0];
			//					int objectID = Integer.parseInt(obID);
			//					double x=Double.parseDouble(lineaDiciembre[5]);
			//					double y=Double.parseDouble(lineaDiciembre[6]);
			//					if(x<xmin)
			//						xmin=x;
			//					else if(x>xmax)
			//						xmax=x;
			//					if(y<ymin)
			//						ymin=y;
			//					else if(y>ymax)
			//						ymax=y;
			//					String address = lineaDiciembre[3];
			//					String streetSegID = lineaDiciembre[4];
			//					String fine = lineaDiciembre[8];
			//					int fineAmt = Integer.parseInt(fine);
			//					String total = lineaDiciembre[9];
			//					int totalPaid = Integer.parseInt(total);
			//					String p1 = lineaDiciembre[10];
			//					int penalty1 = Integer.parseInt(p1);
			//					String p2 = lineaDiciembre[11];
			//					int penalty2 = 0;
			//					if(!p2.equals("")){
			//						penalty2 = Integer.parseInt(p2);
			//					}
			//					else{
			//						penalty2 = 0;
			//					}
			//					String accidentIndicator = lineaDiciembre[12];
			//					String issueDate = lineaDiciembre[14];
			//					String violationCode = lineaDiciembre[15];
			//					String violationDesc = lineaDiciembre[16];
			//
			//					arreglo.agregar(new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,address));						totalPaid, violationDesc, accidentIndicator, penalty1, penalty2));
			//					tot++;
			//					d++;}
			//				System.out.println("Datos de Enero:"+d);
			//				lectorDiciembre.close();
			//				estadistica = new EstadisticasCargaInfracciones(tot, ju, ag, s, o ,n, d, xmin, ymin,xmax,ymax);
			//			}


//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}

		return estadistica;


	}

	/**
	 * Requerimiento 1A: Obtener el ranking de las N franjas horarias
	 * que tengan más infracciones. 
	 * @param int N: Número de franjas horarias que tienen más infracciones
	 * @return Cola con objetos InfraccionesFranjaHoraria
	 */
	public IQueue<InfraccionesFranjaHoraria> rankingNFranjas(int N)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 2A: Consultar  las  infracciones  por
	 * Localización  Geográfica  (Xcoord, Ycoord) en Tabla Hash.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracción
	 *			double yCoord : Coordenada Y de la localizacion de la infracción
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorLocalizacionHash(double xCoord, double yCoord)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 3A: Buscar las infracciones por rango de fechas
	 * @param  LocalDate fechaInicial: Fecha inicial del rango de búsqueda
	 * 		LocalDate fechaFinal: Fecha final del rango de búsqueda
	 * @return Cola con objetos InfraccionesFecha
	 */
	public IQueue<InfraccionesFecha> consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 1B: Obtener  el  ranking  de  las  N  tipos  de  infracción
	 * (ViolationCode)  que  tengan  más infracciones.
	 * @param  int N: Numero de los tipos de ViolationCode con más infracciones.
	 * @return Cola con objetos InfraccionesViolationCode con top N infracciones
	 */
	public IQueue<InfraccionesViolationCode> rankingNViolationCodes(int N)
	{
		// TODO completar
		return null;		
	}


	/**
	 * Requerimiento 2B: Consultar las  infracciones  por  
	 * Localización  Geográfica  (Xcoord, Ycoord) en Arbol.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracción
	 *			double yCoord : Coordenada Y de la localizacion de la infracción
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorLocalizacionArbol(double xCoord, double yCoord)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 3B: Buscar las franjas de fecha-hora donde se tiene un valor acumulado
	 * de infracciones en un rango dado [US$ valor inicial, US$ valor final]. 
	 * @param  double valorInicial: Valor mínimo acumulado de las infracciones
	 * 		double valorFinal: Valor máximo acumulado de las infracciones.
	 * @return Cola con objetos InfraccionesFechaHora
	 */
	public IQueue<InfraccionesFechaHora> consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 1C: Obtener  la información de  una  addressId dada
	 * @param  int addressID: Localización de la consulta.
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorAddressId(int addressID)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 2C: Obtener  las infracciones  en  un  rango de
	 * horas  [HH:MM:SS  inicial,HH:MM:SS  final]
	 * @param  LocalTime horaInicial: Hora  inicial del rango de búsqueda
	 * 		LocalTime horaFinal: Hora final del rango de búsqueda
	 * @return Objeto InfraccionesFranjaHorariaViolationCode
	 */
	public InfraccionesFranjaHorariaViolationCode consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 3C: Obtener  el  ranking  de  las  N localizaciones geográficas
	 * (Xcoord,  Ycoord)  con  la mayor  cantidad  de  infracciones.
	 * @param  int N: Numero de las localizaciones con mayor número de infracciones
	 * @return Cola de objetos InfraccionesLocalizacion
	 */
	public IQueue<InfraccionesLocalizacion> rankingNLocalizaciones(int N)
	{
		// TODO completar
		return null;		
	}

	/**
	 * Requerimiento 4C: Obtener la  información  de  los códigos (ViolationCode) ordenados por su numero de infracciones.
	 * @return Contenedora de objetos InfraccionesViolationCode.
	  // TODO Definir la estructura Contenedora
	 */
	public Contenedora<InfraccionesViolationCode> ordenarCodigosPorNumeroInfracciones()
	{
		// TODO completar
		// TODO Definir la Estructura Contenedora
		return null;		
	}


}
