package info.ogkapps.table21.dto;

public class ItemsDTO {

	public String itemCode;
	public String itemName;
	public String itemCost;
	public String message;

	public ItemsDTO(String itemCode, String itemName, String itemCost) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemCost = itemCost;
	}

	public ItemsDTO(String message) {
		super();
		this.message = message;
	}

	public ItemsDTO() {
		super();
	}
}
