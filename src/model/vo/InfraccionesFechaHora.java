package model.vo;

import java.time.LocalDateTime;
import java.time.LocalTime;

import model.data_structures.IQueue;

public class InfraccionesFechaHora extends EstadisticaInfracciones {

	@Override
	public String toString() {
		return "InfraccionesFechaHora [fecha_hora_inicial=" + fecha_hora_inicial + ",\n fecha_hora_final="
				+ fecha_hora_final + ",\n totalInfracciones=" + totalInfracciones + ",\n porcentajeAccidentes="
				+ porcentajeAccidentes + ",\n porcentajeNoAccidentes=" + porcentajeNoAccidentes + ",\n valorTotal="
				+ valorTotal + "]\n\n";
	}

	private LocalTime fecha_hora_inicial;
	
	private LocalTime fecha_hora_final;
	
	public InfraccionesFechaHora(LocalTime pFechaHoraIni, LocalTime pFechaHoraFin, IQueue<VOMovingViolations> lista) {
		super(lista);
		// TODO Auto-generated constructor stub
		fecha_hora_inicial = pFechaHoraIni; 
		fecha_hora_final = pFechaHoraFin; 
	}

}
