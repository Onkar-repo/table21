package info.ogkapps.table21.dto;

public class BilledItemsDTO {
	
	public String serial;
	public String quantity;
	public String description;
	public String amount;
	public BilledItemsDTO(String serial, String quantity, String description, String amount) {
		super();
		this.serial = serial;
		this.quantity = quantity;
		this.description = description;
		this.amount = amount;
	}
	

}
