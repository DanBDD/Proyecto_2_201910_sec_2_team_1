package model.vo;

/**
 * Representation of a Trip object
 */
public class VOMovingViolations implements Comparable<VOMovingViolations> {

	private int objectID;
	private double totalPaid;
	private String location;
	private String date;
	private String accidentIndicator;
	private String violationDesc;
	private String streetSegID;
	private String addressID;
	private String violationCode;
	private double x;
	private double y;
	
	public VOMovingViolations(int pObID, double totalPaid2, String pLoc, String pDate, String pAccident, String pDesc, String pstreetID, String paddID, String pCode, double x, double y) {
		objectID = pObID;
		totalPaid = totalPaid2;
		location = pLoc;
		date = pDate;
		accidentIndicator = pAccident;
		violationDesc = pDesc;
		streetSegID = pstreetID;
		addressID = paddID;
		violationCode = pCode;
		this.x=x;
		this.y=y;
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
	
	public String getCode(){
		return violationCode;
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
	public double getTotalPaid() {

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
	public double darX()
	{
		return x;
	}
	public double darY()
	{
		return y;
	}

	@Override
	public int compareTo(VOMovingViolations o) {
		double d=this.darX()-o.darX();
		if(d<0)
			return -1;
		else if(d>0)
			return 1;
		else
		{
			double d2=this.darY()-o.darY();
			if(d2<0)
				return -1;
			else if(d2>0)
				return 1;
			return 0;
		}
	}
}
