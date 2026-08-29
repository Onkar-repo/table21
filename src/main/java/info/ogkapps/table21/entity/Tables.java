package info.ogkapps.table21.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Class Definition begins here...
@Entity
public class Tables {

//  Fields begins here...
//  Unnecessary column, added only to fulfill mapping requirement based on (primary key field required)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long tablePk;
	
//  This tableUser is a foreign key reference (Users.userId)
	Long tableUser;
	
	Short tableNumber;
	
	String tableStatus;
	
	Long tableBillId;
	
//  Constructors begins here...
	
public Tables(Long tableUser, Short tableNumber, String tableStatus, Long tableBillId) {
		super();
		this.tableUser = tableUser;
		this.tableNumber = tableNumber;
		this.tableStatus = tableStatus;
		this.tableBillId = tableBillId;
	}	
	
public Tables() {
	super();
}

	//  Getters Setters begins here...
	public Long getTableUser() {
		return tableUser;
	}

	public Long getTableBillId() {
		return tableBillId;
	}

	public void setTableBillId(Long tableBillId) {
		this.tableBillId = tableBillId;
	}

	public void setTableUser(Long tableUser) {
		this.tableUser = tableUser;
	}

	public Short getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Short tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}

	public Long getTablePK() {
		return tablePk;
	}

	public void setTablePK(Long tablePK) {
		this.tablePk = tablePK;
	}

	//  To String begins here...
	@Override
	public String toString() {
		return "Tables [tableUser=" + tableUser + ", tableNumber=" + tableNumber + ", tableStatus=" + tableStatus + "]";
	}
	
}
