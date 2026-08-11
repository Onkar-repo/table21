package info.ogkapps.table21.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//  Class Definition begins here...
@Entity
public class Items {

//  Fields begins here...
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long itemId;
	
	String itemCode;
	
	String itemName;
	
	Integer itemCost;
	
	Short itemGST;
	
//  This itemUser is a foreign key reference (Users.userId)
	Long itemUser;

//  Constructors begins here...
	public Items() {

	}

	public Items(String itemCode, String itemName, Long itemUser) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemUser = itemUser;
	}

//  Getters Setters begins here...
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemCost() {
		return itemCost;
	}

	public void setItemCost(Integer itemCost) {
		this.itemCost = itemCost;
	}

	public Short getItemGST() {
		return itemGST;
	}

	public void setItemGST(Short itemGST) {
		this.itemGST = itemGST;
	}

	public Long getItemUser() {
		return itemUser;
	}

	public void setItemUser(Long itemUser) {
		this.itemUser = itemUser;
	}

//  To String begins here...
	@Override
	public String toString() {
		return "Items [itemCode=" + itemCode + ", itemName=" + itemName + ", itemUser=" + itemUser + "]";
	}

}
