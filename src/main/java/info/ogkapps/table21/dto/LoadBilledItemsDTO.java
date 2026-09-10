package info.ogkapps.table21.dto;

import java.util.List;

public class LoadBilledItemsDTO {
	
	public String billUser;
	public String billTable;
	public String billStatus;
	public String requestType;
	public String billNumber;
	public String tableStatus;
	public String billTotal;
	public List<BilledItemsDTO> itemList;
	public String message;
	public LoadBilledItemsDTO(String message) {
		super();
		this.message = message;
	}
	public LoadBilledItemsDTO() {
		super();
	}
	

}
