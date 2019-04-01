package model.vo;

/**
 * Representation of a Trip object
 */
public class VOMovingViolations {

	private int objectID;
	private int totalPaid;
	private String location;
	private String date;
	private String accidentIndicator;
	private String violationDesc;
	private String streetSegID;
	private String addressID;
	
	public VOMovingViolations(int pObID, int ptotalPaid, String pLoc, String pDate, String pAccident, String pDesc, String pstreetID, String paddID) {
		objectID = pObID;
		totalPaid = ptotalPaid;
		location = pLoc;
		date = pDate;
		accidentIndicator = pAccident;
		violationDesc = pDesc;
		streetSegID = pstreetID;
		addressID = paddID;
	}
	
	@Override
	public String toString() {
		return "VOMovingViolations [objectId()=" + objectId() + ",\n getLocation()=" + getLocation()
				+ ",\n getTicketIssueDate()=" + getTicketIssueDate() + ",\n getTotalPaid()=" + getTotalPaid()
				+ ",\n getAccidentIndicator()=" + getAccidentIndicator() + ",\n getViolationDescription()="
				+ getViolationDescription() + ",\n getStreetSegId()=" + getStreetSegId() + ",\n getAddressId()="
				+ getAddressId() + "]\n\n";
	}


	/**
	 * @return id - Identificador único de la infracción
	 */
	public int objectId() {
		
		return objectID;
	}	
	
	
	/**
	 * @return location - Dirección en formato de texto.
	 */
	public String getLocation() {
		
		return location;
	}

	/**
	 * @return date - Fecha cuando se puso la infracción .
	 */
	public String getTicketIssueDate() {
		
		return date;
	}
	
	/**
	 * @return totalPaid - Cuanto dinero efectivamente pagó el que recibió la infracción en USD.
	 */
	public int getTotalPaid() {

		return totalPaid;
	}
	
	/**
	 * @return accidentIndicator - Si hubo un accidente o no.
	 */
	public String  getAccidentIndicator() {
		
		return accidentIndicator;
	}
		
	/**
	 * @return description - Descripción textual de la infracción.
	 */
	public String  getViolationDescription() {

		return violationDesc;
	}
	
	public String getStreetSegId() {
		return streetSegID;
	}
	
	public String getAddressId() {
		return addressID;
	}
}
