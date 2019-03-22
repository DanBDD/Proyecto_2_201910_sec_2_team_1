package model.vo;

public class LocationVO implements Comparable<LocationVO>{

	private int addressID;

	private String location;

	private int numberOfRegisters;

	public  LocationVO(int a, String l, int num) {
		addressID=a;
		location=l;
		numberOfRegisters=num;
	}
	@Override
	public int compareTo(LocationVO o) {

		if((this.numberOfRegisters - o.numberOfRegisters) > 0)
			return 1;
		else if((this.numberOfRegisters - o.numberOfRegisters) < 0)
			return -1;
		else{
			if(this.location.compareToIgnoreCase(o.location)>0)
				return 1;
			else if(this.location.compareToIgnoreCase(o.location)<0)
				return -1;
			return 0;
		}
	}
	public int darAddressID(){
		return addressID;
	}
	
	public int darNumberOfRegisters(){
		return numberOfRegisters;
	}
	
	public String darLocation(){
		return location;
	}
	
	public String toString(){
		return "AddressID: " + addressID + " Number of registers: " + numberOfRegisters + " location: " + location ;
	}
}


