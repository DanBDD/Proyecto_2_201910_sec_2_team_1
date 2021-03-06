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
	private int addressID;
	private String violationCode;
	private double x;
	private double y;
	private double amt;
	private double penalty1;
	private double penalty2;
	
	public VOMovingViolations(int pObID, double totalPaid2, String pLoc, String pDate, String pAccident, String pDesc, String pstreetID, int paddID, String pCode, double x, double y, double pAmt, double pP1, double pP2) {
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
		amt = pAmt;
		penalty1 = pP1;
		penalty2 = pP2;
	}
	
	@Override
	public String toString() {
		return "VOMovingViolations [objectId()=" + objectId() + ",\n getLocation()=" + getLocation()
				+ ",\n getTicketIssueDate()=" + getTicketIssueDate() + ",\n getTotalPaid()=" + getTotalPaid()
				+ ",\n getAccidentIndicator()=" + getAccidentIndicator() + ",\n getViolationDescription()="
				+ getViolationDescription() + ",\n getStreetSegId()=" + getStreetSegId() + ",\n getAddressId()="
				+ getAddressId() + "]\n\n";
	}

	public double getAmt(){
		return amt;
	}
	
	public double getP1(){
		return penalty1;
	}
	
	public double getP2(){
		return penalty2;
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
	
	public int getAddressId() {
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
