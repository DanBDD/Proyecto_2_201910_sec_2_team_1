package model.data_structures;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.vo.VOMovingViolations;
import model.logic.*;

/**
 * 
 * 
 *
 */
public enum Comparaciones implements Serializable{
	ADDRESSID("AddressID", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;


		//TODO Cree y complete el m�todo compare, de acuerdo a la documentaci�n.
		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			int comparacion=o1.getAddressId() - o2.getAddressId();
			if(comparacion<0)
				return -1;
			else if(comparacion>0)
				return 1;
			else return 0;

		}


	}),
	DATE("Date", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;


		//TODO Cree y complete el m�todo compare, de acuerdo a la documentaci�n.
		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			LocalDateTime d1=ManejoFechaHora.convertirFecha_Hora_LDT(o1.getTicketIssueDate());
			LocalDateTime d2=ManejoFechaHora.convertirFecha_Hora_LDT(o2.getTicketIssueDate());
			int comparacion =d1.compareTo(d2);
			if(comparacion<0)
				return -1;
			else if(comparacion>0)
				return 1;
			else return 0;

		}


	}),
	DATE2("Date2", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;


		//TODO Cree y complete el m�todo compare, de acuerdo a la documentaci�n.
		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			String date1 = o1.getTicketIssueDate().split("T")[0];
			String date2 = o2.getTicketIssueDate().split("T")[0];
			LocalDate d1=ManejoFechaHora.convertirFecha_LD(date1);
			LocalDate d2=ManejoFechaHora.convertirFecha_LD(date2);
			int comparacion =d1.compareTo(d2);
			if(comparacion<0)
				return -1;
			else if(comparacion>0)
				return 1;
			else return 0;

		}


	}),
	VIOLATIONCODE("ViolationCode", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			int comp = 0;
			int cod1 = Integer.parseInt(o1.getCode().substring(1));
			int cod2 = Integer.parseInt(o2.getCode().substring(1));
			if((cod1 - cod2) > 0){
				comp = 1;
			}
			else if((cod1 - cod2) < 0){
				comp = -1;
			}
			else if((cod1 - cod2) == 0){
				comp = 0;
			}
			return comp;
		}


	}),
	DATETIME("DateTime", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			int comp = 0;
			LocalDate dt1 = ManejoFechaHora.convertirFechaHoraLLave(o1.getTicketIssueDate());
			LocalDate dt2 = ManejoFechaHora.convertirFechaHoraLLave(o2.getTicketIssueDate());
			if(dt1.compareTo(dt2) > 0) {
				comp = 1;
			}
			else if(dt1.compareTo(dt2) < 0) {
				comp = -1;
			}
			else if(dt1.compareTo(dt2) == 0) {
				comp = 0;
			}
			return comp;
		}


	}),
	TIME("Time", new SerializableComparator<VOMovingViolations>() {

		private static final long serialVersionUID = 123L;

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			int comp = 0;
			String pre1 = (o1.getTicketIssueDate().split("T")[1]).split("\\.")[0];
			String pre2 = (o2.getTicketIssueDate().split("T")[1]).split("\\.")[0];
			LocalTime t1 = ManejoFechaHora.convertirHora_LT(pre1);
			LocalTime t2 = ManejoFechaHora.convertirHora_LT(pre2);
			if(t1.compareTo(t2) < 0) {
				comp = -1;
			}
			else if(t1.compareTo(t2) > 0) {
				comp = 1;
			}
			else if(t1.compareTo(t2) == 0) {
				comp = 0;
			}
			return comp;
		}


	}),
	CORD("CORD", new SerializableComparator<VOMovingViolations>() {
		/**
		 * 
		 */

		/**
		 * 
		 */
		private static final long serialVersionUID = 1456L;

		@Override
		public int compare(VOMovingViolations o1, VOMovingViolations o2) {
			// TODO Auto-generated method stub
			double d=o1.darX()-o2.darX();
			if(d<0)
				return -1;
			else if(d>0)
				return 1;
			else
			{
				double d2=o1.darY()-o2.darY();
				if(d2<0)
					return -1;
				else if(d2>0)
					return 1;
				return 0;
			}

		}
	});

	/**
	 * Nombre para mostrarle al usuario del nombre del criterio de comparaci�n.
	 */
	public String nombre;

	/**
	 * Criterio de comparaci�n del elemento de la enumeraci�n.
	 */
	public SerializableComparator<VOMovingViolations> comparador;

	/**
	 * Constructor del enum. Asigna el nombre y el comparador.
	 * @param nombre Nombre para mostrarle al usuario.
	 * @param comparador Comparador del elemento del enum.
	 */
	private Comparaciones(String nombre, SerializableComparator<VOMovingViolations> comparador) 
	{
		this.nombre = nombre;
		this.comparador = comparador;
	}

	@Override
	public String toString() 
	{
		return nombre;
	}
}
