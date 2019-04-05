package model.vo;

/**
 * Clase que representa los datos de reporte de la lectura de las infracciones 
 * 
 */
public class EstadisticasCargaInfracciones {
	
	// total de infracciones cargadas
	private int totalInfracciones;
	
	// numero de meses cargados
	private int numeroMeses;
	
	// numero de infraccioens cargadas x cada mes
	private int [] numeroInfraccionesxMes;
	
	// minX es minimax[0], minY es minimax[1], maxX es minimax[2], maxY es minimax[3]
	private double [] minimax;
	
	/**
	 * Constructor con el resumen de la carga de la lectura de las infracciones
	 // TODO definir los parametros necesarios para inicializar los atributos de la clase
	 * @param parametro1 con valor para un dato de la carga de archivos
	 * @param parametro2 con valor para un dato de la carga de archivos
	 * 
	 */
	public EstadisticasCargaInfracciones(int pTotalInfracciones, int pMes1, int pMes2,int pMes3 , int pMes4, int pMes5,int pMes6, double pMinX, double pMinY, double pMaxX, double pMaxY)
	{
		totalInfracciones = pTotalInfracciones;
		numeroMeses =6;
		for(int i=0; i<12; i++){
			if(i==0){
				numeroInfraccionesxMes[i] = pMes1;
				minimax[i] = pMinX;
			}
			else if(i == 1){
				numeroInfraccionesxMes[i] = pMes2;
				minimax[i] = pMinY;
			}
			else if(i == 2){
				numeroInfraccionesxMes[i] = pMes3;
				minimax[i] = pMaxX;
			}
			else if(i == 3){
				numeroInfraccionesxMes[i] = pMes4;
				minimax[i] = pMaxY;
			}
			else if(i == 4){
				numeroInfraccionesxMes[i] = pMes5;
				
			}
			else if(i == 5){
				numeroInfraccionesxMes[i] = pMes6;
			
			}
			
		}
		// TODO Definir los parametros del metodo
		// TODO inicializar valores de atributos con valores de parametros
	}
	
	/**
	 * Retorna el numero de meses que fueron cargados en la lectura de archivos
	 * @return numero de meses que fueron cargados en la lectura de archivos
	 */
	public int darNumeroDeMesesCargados()
	{
		return numeroMeses;
	}
	
	/**
	 * Retorna el numero total de infracciones cargadas 
	 */
	public int darTotalInfracciones()
	{
		return totalInfracciones;
	}
	
	/** 
	 * Retorna el numero de infracciones cargadas por cada mes de lectura de archivos
	 * @return arreglo con el numero de infracciones cargadas por cada mes de lectura de archivos
	 */
	public int [] darNumeroDeInfraccionesXMes()
	{
		return numeroInfraccionesxMes;
	}
	
	/**
	 * Retorna el area Minimax de las infracciones cargadas en la lectura de archivos
	 * @return arreglo con 4 valores double: minX en posicion 0, minY en posicion 1, maxX en posicion 2 y maxY en posicion 3
	 */
	
	public double [] darMinimax()
	{
		return minimax;
	}

	
}
