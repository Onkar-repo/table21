package info.ogkapps.table21.dto;

public class PrintDTO {

	
	public String itemName;
	public String itemQuantity;
	public Integer itemCost;
	
	public PrintDTO(String itemName, String itemQuantity, Integer itemCost) {
		super();
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.itemCost = itemCost;
	}
}
