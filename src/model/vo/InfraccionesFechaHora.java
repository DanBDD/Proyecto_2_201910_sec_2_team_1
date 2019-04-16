package model.vo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import model.data_structures.IQueue;

public class InfraccionesFechaHora extends EstadisticaInfracciones implements Comparable<InfraccionesFechaHora> {

	@Override
	public String toString() {
		return "InfraccionesFechaHora [fecha_hora_inicial=" + fecha_hora_inicial + ",\n fecha_hora_final="
				+ fecha_hora_final + ",\n totalInfracciones=" + totalInfracciones + ",\n porcentajeAccidentes="
				+ porcentajeAccidentes + ",\n porcentajeNoAccidentes=" + porcentajeNoAccidentes + ",\n valorTotal="
				+ total + "]\n\n";
	}

	private LocalTime fecha_hora_inicial;
	
	private double total;
	
	private LocalTime fecha_hora_final;
	
	public InfraccionesFechaHora(LocalTime pFechaHoraIni, LocalTime pFechaHoraFin, IQueue<VOMovingViolations> lista, double total) {
		super(lista);
		// TODO Auto-generated constructor stub
		fecha_hora_inicial = pFechaHoraIni; 
		fecha_hora_final = pFechaHoraFin; 
		this.total=total;	
	}

	public double t()
	{
		return total;
	}
	@Override
	public int compareTo(InfraccionesFechaHora o) {
		// TODO Auto-generated method stub
		if(this.totalInfracciones-o.totalInfracciones<0)
			return 1;
		else if(this.totalInfracciones-o.totalInfracciones>0)
			return -1;
		return 0;
	}

}
