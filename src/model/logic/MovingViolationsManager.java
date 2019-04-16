package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.core.SubstringMatcher;

import com.opencsv.CSVReader;
import model.data_structures.ArregloDinamico;
import model.data_structures.Cola;
import model.data_structures.Comparaciones;
import model.data_structures.IQueue;
import model.data_structures.LinearProbing;
import model.data_structures.MaxColaPrioridad;
import model.data_structures.MaxHeapCP;
import model.data_structures.RedBlackBST;
import model.data_structures.SeparateChaining;
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
	private Comparable<VOMovingViolations> [ ] muestra;
	private RedBlackBST<LocalTime, VOMovingViolations> tree;
	private LinearProbing<Integer, InfraccionesLocalizacion> linear;
	private ArregloDinamico<VOMovingViolations> q;
	private IQueue<InfraccionesLocalizacion> resReqC;
	private IQueue<VOMovingViolations> auxReqC;
	private MaxColaPrioridad<InfraccionesLocalizacion> preReqC;
	private MaxHeapCP<InfraccionesViolationCode> resReq4C;
	/**
	 * Metodo constructor
	 */
	public MovingViolationsManager()
	{
		arreglo = new ArregloDinamico<>(35000);
		sem1 = new String[6];
		sem2 = new String[6];
		tree = new  RedBlackBST<>();
		linear=new LinearProbing<Integer, InfraccionesLocalizacion>(4);
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
			if(numeroSemestre ==2){

				for(int i = 0;i<sem2.length;i++){
					contMes = 0;
					String mes = sem2[i];
					CSVReader lector = new CSVReader(new FileReader(mes));
					String[] linea = lector.readNext();
					while ((linea = lector.readNext()) != null) {

						String obID = linea[0];
						String location = linea[2];
						int objectID = Integer.parseInt(obID);
						double x=Double.parseDouble(linea[5]);
						double y=Double.parseDouble(linea[6]);
						if(x<xmin || xmin == 0)
							xmin=x;
						else if(x>xmax || xmax == 0)
							xmax=x;
						if(y<ymin || ymin == 0)
							ymin=y;
						else if(y>ymax || ymax == 0)
							ymax=y;
						String address = linea[3];
						if (address.equals(""))
						{
							address ="0";
						}
						int pAdd = Integer.parseInt(address);
						String streetSegID = linea[4];
						if(streetSegID.equals(""))
						{
							streetSegID="0";
						}
						String amt = linea[8];
						double pamt = Integer.parseInt(amt);
						String total = linea[9];
						double totalPaid = Double.parseDouble(total);
						String p1 = linea[10];
						double penalty1 = Double.parseDouble(p1);
						String p2 = linea[11];
						double penalty2 = 0;
						if(!p2.equals("")){
							penalty2 = Double.parseDouble(p2);
						}
						else{
							penalty2 = 0;
						}

						String accidentIndicator = linea[12];
						String issueDate = "";
						String violationCode ="";
						String violationDesc = "";
						if(linea[13].length()<5)
						{
							issueDate = linea[14];
							violationCode = linea[15];
							violationDesc = linea[16];
						}
						else
						{
							issueDate=linea[13];
							violationCode = linea[14];
							violationDesc = linea[15];
						}

						VOMovingViolations vo = new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,pAdd, violationCode,x,y,pamt,penalty1,penalty2);
						arreglo.agregar(vo);
						String pre1 = (issueDate.split("T")[1]).split("\\.")[0];
						LocalTime t1 = ManejoFechaHora.convertirHora_LT(pre1);
						tree.put(t1, vo);
						totalNuevo2++;
						contMes++;
						if(i == 0){
							ju=contMes;
						}
						else if(i == 1){
							ag=contMes;
						}
						else if(i == 2){
							s=contMes;

						}
						else if(i == 3){
							o=contMes;

						}
						else if(i == 4){
							n=contMes;

						}
						else if(i == 5){
							d=contMes;

						}
					}

					lector.close();
				}
				estadistica = new EstadisticasCargaInfracciones(totalNuevo2, ju, ag, s,o ,n, d, xmin, ymin,xmax,ymax);
			}

			else{
				for(int i = 0;i<sem1.length;i++){
					contMes = 0;
					String mes = sem1[i];
					CSVReader lector = new CSVReader(new FileReader(mes));
					String[] linea = lector.readNext();
					while ((linea = lector.readNext()) != null) {

						String obID = linea[0];
						String location = linea[2];
						int objectID = Integer.parseInt(obID);
						double x=Double.parseDouble(linea[5]);
						double y=Double.parseDouble(linea[6]);
						if(x<xmin || xmin == 0)
							xmin=x;
						else if(x>xmax || xmax== 0)
							xmax=x;
						if(y<ymin || ymin == 0)
							ymin=y;
						else if(y>ymax || ymax == 0)
							ymax=y;
						String address = linea[3];
						if (address.equals(""))
						{
							address ="0";
						}
						int pAdd = Integer.parseInt(address);
						String streetSegID = linea[4];
						if(streetSegID.equals(""))
						{
							streetSegID="0";
						}
						String amt = linea[8];
						double pamt = Integer.parseInt(amt);
						String total = linea[9];
						double totalPaid = Double.parseDouble(total);
						String p1 = linea[10];
						double penalty1 = Double.parseDouble(p1);
						String p2 = linea[11];
						double penalty2 = 0;
						if(!p2.equals("")){
							penalty2 = Double.parseDouble(p2);
						}
						else{
							penalty2 = 0;
						}

						String accidentIndicator = linea[12];
						String issueDate = "";
						String violationCode ="";
						String violationDesc = "";
						if(linea[13].length()<5)
						{
							issueDate = linea[14];
							violationCode = linea[15];
							violationDesc = linea[16];
						}
						else
						{
							issueDate=linea[13];
							violationCode = linea[14];
							violationDesc = linea[15];
						}
						VOMovingViolations vo = new VOMovingViolations(objectID,totalPaid, location,issueDate, accidentIndicator, violationDesc, streetSegID,pAdd, violationCode,x,y,pamt,penalty1,penalty2);
						arreglo.agregar(vo);
						String pre1 = (issueDate.split("T")[1]).split("\\.")[0];
						LocalTime t1 = ManejoFechaHora.convertirHora_LT(pre1);
						tree.put(t1, vo);
						totalNuevo1++;
						contMes++;
						if(i == 0){
							enero=contMes;
						}
						else if(i == 1){
							f=contMes;
						}
						else if(i == 2){
							m=contMes;

						}
						else if(i == 3){
							a=contMes;

						}
						else if(i == 4){
							mayo=contMes;

						}
						else if(i == 5){
							j=contMes;

						}
					}

					lector.close();
				}
				estadistica = new EstadisticasCargaInfracciones(totalNuevo1, enero, f, m, a ,mayo, j, xmin, ymin,xmax,ymax);	
			}
			if(linear != null) {
				linear = new LinearProbing<Integer, InfraccionesLocalizacion>(100);
			}
			generarMuestra(arreglo.darTamano());

			Comparable[] copia = muestra;
			Sort.ordenarMergeSort(copia, Comparaciones.ADDRESSID.comparador, true);
			IQueue<VOMovingViolations> cola = new Cola<>();
			for (int i = 0; i< copia.length - 1; i++) {
				VOMovingViolations actual=(VOMovingViolations) copia[i];
				VOMovingViolations siguiente = (VOMovingViolations) copia[i+1];
				if((actual.getAddressId() - siguiente.getAddressId()) == 0) {
					cola.enqueue(actual);
					if(i+1 == copia.length) {
						cola.enqueue(siguiente);
						InfraccionesLocalizacion c ;
						c=new InfraccionesLocalizacion(actual.darX(), actual.darY(),actual.getLocation(), actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), cola);
						linear.put(actual.getAddressId(), c);
						cola=null;
						cola= new Cola<>();
					}
				}
				else if((actual.getAddressId() - siguiente.getAddressId()) != 0)
				{
					cola.enqueue(actual);
					InfraccionesLocalizacion c ;
					c=new InfraccionesLocalizacion(actual.darX(), actual.darY(),actual.getLocation(), actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), cola);
					linear.put(actual.getAddressId(), c);
					cola=null;
					cola= new Cola<>();
				}
			}
			resReqC = new Cola<>();
			auxReqC = new Cola<>();
			preReqC = new MaxColaPrioridad<>();
			Comparable[] copia2 = muestra;
			Comparator comp = Comparaciones.CORD.comparador;
			Sort.ordenarMergeSort(copia2, comp, true);
			for(int i = 0; i<copia2.length-1; i++){
				VOMovingViolations actual = (VOMovingViolations) copia2[i];
				VOMovingViolations sig = (VOMovingViolations) copia2[i+1];

				if((comp.compare(actual, sig)) == 0){
					auxReqC.enqueue(actual);
					if(i+1 == copia2.length) {
						auxReqC.enqueue(sig);
						InfraccionesLocalizacion elem = new InfraccionesLocalizacion(actual.darX(), actual.darY(), actual.getLocation() , actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), auxReqC);
						preReqC.agregar(elem);
						auxReqC = null;
						auxReqC = new Cola<>();
					}
				}
				else if(comp.compare(actual, sig) != 0){
					auxReqC.enqueue(actual);
					InfraccionesLocalizacion elem = new InfraccionesLocalizacion(actual.darX(), actual.darY(), actual.getLocation() , actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), auxReqC);
					preReqC.agregar(elem);
					auxReqC = null;
					auxReqC = new Cola<>();
				}
			}
			resReq4C = new MaxHeapCP<>();
			Comparable[] copia3 = muestra;
			Sort.ordenarMergeSort(copia3, Comparaciones.VIOLATIONCODE.comparador, true);
			IQueue<VOMovingViolations> colaReq4C = new Cola<>();
			for(int v = 0; v<copia3.length - 1;v++) {
				VOMovingViolations actual = (VOMovingViolations) copia3[v];
				VOMovingViolations sig =  (VOMovingViolations) copia3[v+1];
				if(actual.getCode().equals(sig.getCode())) {
					colaReq4C.enqueue(actual);
					if(v+1 == copia3.length) {
						colaReq4C.enqueue(sig);
						InfraccionesViolationCode infra = new InfraccionesViolationCode(actual.getCode(), colaReq4C);
						resReq4C.agregar(infra);
						colaReq4C = null;
						colaReq4C = new Cola<>();
					}
				}
				else if(!(actual.getCode().equals(sig.getCode()))) {
					colaReq4C.enqueue(actual);
					InfraccionesViolationCode infra = new InfraccionesViolationCode(actual.getCode(), colaReq4C);
					resReq4C.agregar(infra);
					colaReq4C = null;
					colaReq4C = new Cola<>();
				}
			}
		}
		catch (IOException e) {

			e.printStackTrace();
		}
		return estadistica;
	}
	/**
	 * Requerimiento 1A: Obtener el ranking de las N franjas horarias
	 * que tengan m�s infracciones. 
	 * @param int N: N�mero de franjas horarias que tienen m�s infracciones
	 * @return Cola con objetos InfraccionesFranjaHoraria
	 */
	public IQueue<InfraccionesFranjaHoraria> rankingNFranjas(int N)
	{
		// TODO completar
		MaxColaPrioridad<InfraccionesFranjaHoraria> c= new MaxColaPrioridad<>();
		IQueue<InfraccionesFranjaHoraria> retorno = new Cola<>();
		//Una por cada franja horaria
		IQueue<VOMovingViolations> cero=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> uno=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> dos=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> tres=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> cuatro=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> cinco=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> seis=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> siete=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> ocho=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> nueve=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> diez=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> once=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> doce=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> trece=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> catorce=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> quince=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> diesiceis=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> diesiete=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> docho=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> dnueve=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> v=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> vuno=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> vdos=new Cola<VOMovingViolations>();
		IQueue<VOMovingViolations> vtres=new Cola<VOMovingViolations>();
		for(int i=0;i<arreglo.darTamano();i++)
		{
			VOMovingViolations actual = arreglo.darElem(i);
			String num =actual.getTicketIssueDate().split(":")[0].split("T")[1];
			if(num.equals("00"))
			{
				cero.enqueue(actual);
			}
			else if(num.equals("01"))
			{
				uno.enqueue(actual);
			}
			else if(num.equals("02"))
			{
				dos.enqueue(actual);
			}
			else if(num.equals("03"))
			{
				tres.enqueue(actual);
			}
			else if(num.equals("04"))
			{
				cuatro.enqueue(actual);
			}
			else if(num.equals("05"))
			{
				cinco.enqueue(actual);
			}
			else if(num.equals("06"))
			{
				seis.enqueue(actual);
			}
			else if(num.equals("07"))
			{
				siete.enqueue(actual);
			}
			else if(num.equals("08"))
			{
				ocho.enqueue(actual);
			}
			else if(num.equals("09"))
			{
				nueve.enqueue(actual);
			}
			else if(num.equals("10"))
			{
				diez.enqueue(actual);
			}
			else if(num.equals("11"))
			{
				once.enqueue(actual);
			}
			else if(num.equals("12"))
			{
				doce.enqueue(actual);
			}
			else if(num.equals("13"))
			{
				trece.enqueue(actual);
			}
			else if(num.equals("14"))
			{
				catorce.enqueue(actual);
			}
			else if(num.equals("15"))
			{
				quince.enqueue(actual);
			}
			else if(num.equals("16"))
			{
				diesiceis.enqueue(actual);
			}
			else if(num.equals("17"))
			{
				diesiete.enqueue(actual);
			}
			else if(num.equals("18"))
			{
				docho.enqueue(actual);
			}
			else if(num.equals("19"))
			{
				dnueve.enqueue(actual);
			}
			else if(num.equals("20"))
			{
				v.enqueue(actual);
			}
			else if(num.equals("21"))
			{
				vuno.enqueue(actual);
			}
			else if(num.equals("22"))
			{
				vdos.enqueue(actual);
			}
			else if(num.equals("23"))
			{
				vtres.enqueue(actual);
			}
		}		
		InfraccionesFranjaHoraria e0= new InfraccionesFranjaHoraria(LocalTime.parse("00:00:00"), LocalTime.parse("00:59:59"), cero);
		InfraccionesFranjaHoraria e1= new InfraccionesFranjaHoraria(LocalTime.parse("01:00:00"), LocalTime.parse("01:59:59"), uno);
		InfraccionesFranjaHoraria e2= new InfraccionesFranjaHoraria(LocalTime.parse("02:00:00"), LocalTime.parse("02:59:59"), dos);
		InfraccionesFranjaHoraria e3= new InfraccionesFranjaHoraria(LocalTime.parse("03:00:00"), LocalTime.parse("03:59:59"), tres);
		InfraccionesFranjaHoraria e4= new InfraccionesFranjaHoraria(LocalTime.parse("04:00:00"), LocalTime.parse("04:59:59"), cuatro);
		InfraccionesFranjaHoraria e5= new InfraccionesFranjaHoraria(LocalTime.parse("05:00:00"), LocalTime.parse("05:59:59"), cinco);
		InfraccionesFranjaHoraria e6= new InfraccionesFranjaHoraria(LocalTime.parse("06:00:00"), LocalTime.parse("06:59:59"), seis);
		InfraccionesFranjaHoraria e7= new InfraccionesFranjaHoraria(LocalTime.parse("07:00:00"), LocalTime.parse("07:59:59"), siete);
		InfraccionesFranjaHoraria e8= new InfraccionesFranjaHoraria(LocalTime.parse("08:00:00"), LocalTime.parse("08:59:59"), ocho);
		InfraccionesFranjaHoraria e9= new InfraccionesFranjaHoraria(LocalTime.parse("09:00:00"), LocalTime.parse("09:59:59"), nueve);
		InfraccionesFranjaHoraria e10= new InfraccionesFranjaHoraria(LocalTime.parse("10:00:00"), LocalTime.parse("10:59:59"), diez);
		InfraccionesFranjaHoraria e11= new InfraccionesFranjaHoraria(LocalTime.parse("11:00:00"), LocalTime.parse("11:59:59"), once);
		InfraccionesFranjaHoraria e12= new InfraccionesFranjaHoraria(LocalTime.parse("12:00:00"), LocalTime.parse("12:59:59"), doce);
		InfraccionesFranjaHoraria e13= new InfraccionesFranjaHoraria(LocalTime.parse("13:00:00"), LocalTime.parse("13:59:59"), trece);
		InfraccionesFranjaHoraria e14= new InfraccionesFranjaHoraria(LocalTime.parse("14:00:00"), LocalTime.parse("14:59:59"), catorce);
		InfraccionesFranjaHoraria e15= new InfraccionesFranjaHoraria(LocalTime.parse("15:00:00"), LocalTime.parse("15:59:59"), quince);
		InfraccionesFranjaHoraria e16= new InfraccionesFranjaHoraria(LocalTime.parse("16:00:00"), LocalTime.parse("16:59:59"), diesiceis);
		InfraccionesFranjaHoraria e17= new InfraccionesFranjaHoraria(LocalTime.parse("17:00:00"), LocalTime.parse("17:59:59"), diesiete);
		InfraccionesFranjaHoraria e18= new InfraccionesFranjaHoraria(LocalTime.parse("18:00:00"), LocalTime.parse("18:59:59"), docho);
		InfraccionesFranjaHoraria e19= new InfraccionesFranjaHoraria(LocalTime.parse("19:00:00"), LocalTime.parse("19:59:59"), dnueve);
		InfraccionesFranjaHoraria e20= new InfraccionesFranjaHoraria(LocalTime.parse("20:00:00"), LocalTime.parse("20:59:59"), v);
		InfraccionesFranjaHoraria e21= new InfraccionesFranjaHoraria(LocalTime.parse("21:00:00"), LocalTime.parse("21:59:59"), vuno);
		InfraccionesFranjaHoraria e22= new InfraccionesFranjaHoraria(LocalTime.parse("22:00:00"), LocalTime.parse("22:59:59"), vdos);
		InfraccionesFranjaHoraria e23= new InfraccionesFranjaHoraria(LocalTime.parse("23:00:00"), LocalTime.parse("23:59:59"), vtres);

		c.agregar(e0);
		c.agregar(e1);
		c.agregar(e2);
		c.agregar(e3);
		c.agregar(e4);
		c.agregar(e5);
		c.agregar(e6);
		c.agregar(e7);
		c.agregar(e8);
		c.agregar(e9);
		c.agregar(e10);
		c.agregar(e11);
		c.agregar(e12);
		c.agregar(e13);
		c.agregar(e14);
		c.agregar(e15);
		c.agregar(e16);
		c.agregar(e17);
		c.agregar(e18);
		c.agregar(e19);
		c.agregar(e20);
		c.agregar(e21);
		c.agregar(e22);
		c.agregar(e23);
		for (int i=0;i<N;i++)
		{
			retorno.enqueue(c.delMax());
		}
		return retorno;		
	}
	/**
	 * Requerimiento 2A: Consultar  las  infracciones  por
	 * Localizaci�n  Geogr�fica  (Xcoord, Ycoord) en Tabla Hash.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracci�n
	 *			double yCoord : Coordenada Y de la localizacion de la infracci�n
	 * @return Objeto InfraccionesLocalizacion
	 */			
	public InfraccionesLocalizacion consultarPorLocalizacionHash(double xCoord, double yCoord)
	{
		Comparable<VOMovingViolations>[] copia = muestra;
		Sort.ordenarMergeSort(copia, Comparaciones.CORD.comparador, true);
		LinearProbing<Double, InfraccionesLocalizacion> tabla = new LinearProbing<>(100);
		int contador=0;
		IQueue<VOMovingViolations> q= new Cola<>();
		contador++;
		for (int k = 0; k < copia.length-1; k++) {
			contador++;
			VOMovingViolations actual= (VOMovingViolations) copia[k];
			VOMovingViolations siguiente= (VOMovingViolations) copia[k+1];
			if(actual.darX()*actual.darY()+actual.darX()==siguiente.darX()*siguiente.darY()+siguiente.darY())
			{
				q.enqueue(actual);
				if(contador==copia.length)
				{
					q.enqueue(siguiente);
					InfraccionesLocalizacion infra = new InfraccionesLocalizacion(actual.darX(), actual.darY(), actual.getLocation(), actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), q);
					tabla.put(actual.darX()*actual.darY()+actual.darX(), infra);
					q=null;
					q= new Cola<>();
				}
			}
			else if(actual.darX()*actual.darY()+actual.darX()!=siguiente.darX()*siguiente.darY()+siguiente.darY() )
			{
				q.enqueue(actual);
				InfraccionesLocalizacion infra = new InfraccionesLocalizacion(actual.darX(), actual.darY(), actual.getLocation(), actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), q);
				tabla.put(actual.darX()*actual.darY()+actual.darX(), infra);
				q=null;
				q= new Cola<>();
			}
		}
		return tabla.get(xCoord*yCoord+xCoord);
	}
	@SuppressWarnings("unchecked")
	public Comparable<VOMovingViolations> [ ] generarMuestra( int n )
	{
		muestra=null;
		muestra = new Comparable[ n ];
		// TODO Llenar la muestra aleatoria con los datos guardados en la estructura de datos
		ArregloDinamico<VOMovingViolations> e = arreglo;
		int pos=0;
		while(pos<n)
		{
			muestra[pos] = e.darElem(pos);
			pos++;
		}
		return muestra;
	}
	/**
	 * Requerimiento 3A: Buscar las infracciones por rango de fechas
	 * @param  LocalDate fechaInicial: Fecha inicial del rango de b�squeda
	 * 		LocalDate fechaFinal: Fecha final del rango de b�squeda
	 * @return Cola con objetos InfraccionesFecha
	 */
	public IQueue<InfraccionesFecha> consultarInfraccionesPorRangoFechas(LocalDate fechaInicial, LocalDate fechaFinal)
	{
		IQueue<InfraccionesFecha> res = new Cola<>();
		IQueue<VOMovingViolations> pre = new Cola<>();
		RedBlackBST<ChronoLocalDate, InfraccionesFecha> arbolito = new RedBlackBST<>();
		Comparable[] copia = muestra;
		Sort.ordenarMergeSort(copia, Comparaciones.DATE2.comparador, true);
		for(int i=0; i<copia.length-1;i++) {
			VOMovingViolations actual = (VOMovingViolations) copia[i];
			VOMovingViolations sig = (VOMovingViolations) copia[i+1];

			if((ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]).compareTo(ManejoFechaHora.convertirFecha_LD(sig.getTicketIssueDate().split("T")[0]))) == 0){
				pre.enqueue(actual);
				if(i+1 == copia.length) {
					pre.enqueue(sig);
					InfraccionesFecha aja = new InfraccionesFecha(pre,ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]));
					arbolito.put(ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]), aja);
					pre = null;
					pre = new Cola<>();
				}
			}
			else if((ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]).compareTo(ManejoFechaHora.convertirFecha_LD(sig.getTicketIssueDate().split("T")[0]))) != 0) {
				pre.enqueue(actual);
				InfraccionesFecha aja = new InfraccionesFecha(pre,ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]));
				arbolito.put(ManejoFechaHora.convertirFecha_LD(actual.getTicketIssueDate().split("T")[0]), aja);
				pre = null;
				pre = new Cola<>();
			}
		}
		return arbolito.valuesQueue(fechaInicial, fechaFinal);
	}
	/**
	 * Requerimiento 1B: Obtener  el  ranking  de  las  N  tipos  de  infracci�n
	 * (ViolationCode)  que  tengan  m�s infracciones.
	 * @param  int N: Numero de los tipos de ViolationCode con m�s infracciones.
	 * @return Cola con objetos InfraccionesViolationCode con top N infracciones
	 */
	public IQueue<InfraccionesViolationCode> rankingNViolationCodes(int N)
	{
		IQueue<InfraccionesViolationCode> res = new Cola<>();
		IQueue<VOMovingViolations> aux = new Cola<>();
		MaxColaPrioridad<InfraccionesViolationCode> pre = new MaxColaPrioridad<>();
		Comparable[] copia = muestra;
		Sort.ordenarMergeSort(copia, Comparaciones.VIOLATIONCODE.comparador, true);
		for(int i = 0; i<copia.length-1; i++){
			VOMovingViolations actual = (VOMovingViolations) copia[i];
			VOMovingViolations sig = (VOMovingViolations) copia[i+1];

			if(actual.getCode().equals(sig.getCode())){
				aux.enqueue(actual);
				if(i+1 == copia.length) {
					aux.enqueue(sig);
					InfraccionesViolationCode elem = new InfraccionesViolationCode(actual.getCode(), aux);
					pre.agregar(elem);
					aux = null;
					aux = new Cola<>();
				}
			}
			else{
				aux.enqueue(actual);
				InfraccionesViolationCode elem = new InfraccionesViolationCode(actual.getCode(), aux);
				pre.agregar(elem);
				aux = null;
				aux = new Cola<>();
			}
		}
		for(int j = 0; j<N; j++){
			res.enqueue(pre.delMax());
		}
		return res;
	}
	/**
	 * Requerimiento 2B: Consultar las  infracciones  por  
	 * Localizaci�n  Geogr�fica  (Xcoord, Ycoord) en Arbol.
	 * @param  double xCoord : Coordenada X de la localizacion de la infracci�n
	 *			double yCoord : Coordenada Y de la localizacion de la infracci�n
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorLocalizacionArbol(double xCoord, double yCoord)
	{
		RedBlackBST<Double, InfraccionesLocalizacion> arbol = new RedBlackBST<Double, InfraccionesLocalizacion>();
		Comparable[] copia = muestra;
		Sort.ordenarMergeSort(copia, Comparaciones.CORD.comparador, true);
		IQueue<VOMovingViolations> cola = new Cola<>();
		for (int i = 1; i < copia.length-1; i++) {
			VOMovingViolations actual=(VOMovingViolations) copia[i];
			if(actual.compareTo((VOMovingViolations) copia[i+1])== 0) {
				cola.enqueue(actual);
				if(i+1 == copia.length) {
					cola.enqueue((VOMovingViolations) copia[i+1]);
					InfraccionesLocalizacion c ;
					c=new InfraccionesLocalizacion(actual.darX(), actual.darY(),actual.getLocation(),actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), cola);
					arbol.put(actual.darX()+actual.darY(), c);
					cola=null;
					cola= new Cola<>();
				}
			}
			else if(actual.compareTo((VOMovingViolations) copia[i+1])!= 0)
			{
				cola.enqueue(actual);
				InfraccionesLocalizacion c ;
				c=new InfraccionesLocalizacion(actual.darX(), actual.darY(),actual.getLocation(), actual.getAddressId(), Integer.parseInt(actual.getStreetSegId()), cola);
				arbol.put(actual.darX()+actual.darY(), c);
				cola=null;
				cola= new Cola<>();
			}
		}
		return arbol.get(xCoord+yCoord);		
	}
	/**
	 * Requerimiento 3B: Buscar las franjas de fecha-hora donde se tiene un valor acumulado
	 * de infracciones en un rango dado [US$ valor inicial, US$ valor final]. 
	 * @param  double valorInicial: Valor m�nimo acumulado de las infracciones
	 * 		double valorFinal: Valor m�ximo acumulado de las infracciones.
	 * @return Cola con objetos InfraccionesFechaHora
	 */
	public IQueue<InfraccionesFechaHora> consultarFranjasAcumuladoEnRango(double valorInicial, double valorFinal)
	{
		SeparateChaining<String, IQueue<VOMovingViolations>> tabla = new SeparateChaining<>(1000);
		RedBlackBST<Double, InfraccionesFechaHora> arbol = new RedBlackBST<>();
		String llave = null;
		for(int i = 0; i< arreglo.darTamano();i++) {
			VOMovingViolations actual = arreglo.darElem(i);
			llave = actual.getTicketIssueDate().substring(0, 13);
			if(tabla.contains(llave)) {
				tabla.get(llave).enqueue(actual);
			}
			else {
				Cola<VOMovingViolations> cola = new Cola<>();
				cola.enqueue(actual);
				tabla.put(llave, cola);
			}
		}
		Iterator<String> it = tabla.keys().iterator();
		String act=null;
		IQueue<VOMovingViolations> cola = new Cola<>();
		IQueue<VOMovingViolations> aux = new Cola<>();
		String rangoIn = null;
		String rangoFin = null;
		double tot = 0.0;
		while(it.hasNext()) {

			act = it.next();
			cola = tabla.get(act);
			aux = cola;
			for(int i=0;i<aux.size();i++) {
				VOMovingViolations actual = aux.dequeue();
				tot += actual.getAmt() + actual.getP1()+actual.getP2();
			}
			rangoIn = act.substring(11, 13) + ":00:00";
			rangoFin = act.substring(11, 13) + ":59:59";
			InfraccionesFechaHora infra = new InfraccionesFechaHora(ManejoFechaHora.convertirHora_LT(rangoIn), ManejoFechaHora.convertirHora_LT(rangoFin), cola,tot);
			arbol.put(tot, infra);
			tot = 0;
		}
		MaxHeapCP<InfraccionesFechaHora> h= new MaxHeapCP<>();
		IQueue<InfraccionesFechaHora> in= arbol.valuesQueue(valorInicial, valorFinal);
		IQueue<InfraccionesFechaHora> re= new Cola<>();
		while(in.size()>0) 
		{
			InfraccionesFechaHora act1= in.dequeue();
			if(act1.t()<valorFinal && act1.t()>valorInicial)
				h.agregar(act1);
		}
		while( h.darNumElementos()>0) 
		{
			re.enqueue(h.delMax());
		}
		return re;
	}
	/**
	 * Requerimiento 1C: Obtener  la informaci�n de  una  addressId dada
	 * @param  int addressID: Localizaci�n de la consulta.
	 * @return Objeto InfraccionesLocalizacion
	 */
	public InfraccionesLocalizacion consultarPorAddressId(int addressID)
	{
		return linear.get(addressID);		
	}
	/**
	 * Requerimiento 2C: Obtener  las infracciones  en  un  rango de
	 * horas  [HH:MM:SS inicial,HH:MM:SS final]
	 * @param  LocalTime horaInicial: Hora  inicial del rango de b�squeda
	 * 		LocalTime horaFinal: Hora final del rango de b�squeda
	 * @return Objeto InfraccionesFranjaHorariaViolationCode
	 */
	public InfraccionesFranjaHorariaViolationCode consultarPorRangoHoras(LocalTime horaInicial, LocalTime horaFinal)
	{
		IQueue<VOMovingViolations> cola = tree.valuesQueue(horaInicial, horaFinal);
		IQueue<VOMovingViolations> parametro = cola;
		Comparable[] aux = generarMuestra(cola.size());
		Sort.ordenarMergeSort(aux, Comparaciones.VIOLATIONCODE.comparador, true);		
		IQueue<VOMovingViolations> parametroEnSerio = new Cola<>();
		IQueue<InfraccionesViolationCode> parametroALoBien = new Cola<>();
		for(int j = 0; j<aux.length-1;j++) {
			VOMovingViolations actual1 = (VOMovingViolations)aux[j];
			VOMovingViolations sig1 = (VOMovingViolations)aux[j+1];

			if(actual1.getCode().equals(sig1.getCode())) {

				parametroEnSerio.enqueue(actual1);
				if(j+1==aux.length) {
					parametroEnSerio.enqueue(sig1);
					InfraccionesViolationCode infra = new InfraccionesViolationCode(actual1.getCode(), parametroEnSerio);
					parametroALoBien.enqueue(infra);
					parametroEnSerio = null;
					parametroEnSerio = new Cola<>();
				}
			}
			else if(!(actual1.getCode().equals(sig1.getCode()))) {
				parametroEnSerio.enqueue(actual1);
				InfraccionesViolationCode infra = new InfraccionesViolationCode(actual1.getCode(), parametroEnSerio);
				parametroALoBien.enqueue(infra);
				parametroEnSerio = null;
				parametroEnSerio = new Cola<>();
			}
		}
		return new InfraccionesFranjaHorariaViolationCode(horaInicial, horaFinal, parametro, parametroALoBien);		
	}
	/**
	 * Requerimiento 3C: Obtener  el  ranking  de  las  N localizaciones geogr�ficas
	 * (Xcoord,  Ycoord)  con  la mayor  cantidad  de  infracciones.
	 * @param  int N: Numero de las localizaciones con mayor n�mero de infracciones
	 * @return Cola de objetos InfraccionesLocalizacion
	 */
	public IQueue<InfraccionesLocalizacion> rankingNLocalizaciones(int N)
	{
		for(int j = 0; j<N; j++){
			resReqC.enqueue(preReqC.delMax());
		}
		return resReqC;

	}
	/**
	 * Requerimiento 4C: Obtener la  informaci�n  de  los c�digos (ViolationCode) ordenados por su numero de infracciones.
	 * @return Contenedora de objetos InfraccionesViolationCode.
	  // TODO Definir la estructura Contenedora
	 */
	public MaxHeapCP<InfraccionesViolationCode> ordenarCodigosPorNumeroInfracciones()
	{
		return resReq4C;		
	}
}
