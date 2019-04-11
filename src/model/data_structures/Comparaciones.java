package model.data_structures;

import java.io.Serializable;
import java.time.LocalDateTime;

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
			int comparacion=o1.getAddressId().compareTo(o2.getAddressId());
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
