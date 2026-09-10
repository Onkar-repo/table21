package info.ogkapps.table21.dto;

public class PrintDTO {

	
	public String itemName;
	public String itemQuantity;
	public Integer itemCost;
	public String message;
	
	public PrintDTO() {
		super();
	}

	public PrintDTO(String message) {
		super();
		this.message = message;
	}

	public PrintDTO(String itemName, String itemQuantity, Integer itemCost) {
		super();
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.itemCost = itemCost;
	}
}
