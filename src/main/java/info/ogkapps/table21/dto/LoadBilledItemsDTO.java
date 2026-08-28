package info.ogkapps.table21.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import info.ogkapps.table21.entity.BilledItems;

public class LoadBilledItemsDTO {
	
	public String billUser;
	public String billTable;
	public String billStatus;
	public String requestType;
	public String billNumber;
	public String tableStatus;
	public String billTotal;
	
	@JsonSetter(nulls = Nulls.AS_EMPTY)
	public BilledItems itemList[];

}
