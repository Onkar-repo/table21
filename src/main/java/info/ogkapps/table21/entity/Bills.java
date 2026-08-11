package info.ogkapps.table21.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Class Definition begins here...
@Entity
public class Bills {

//  Fields begins here...
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long billId;

//  This billUser is a foreign key reference (Users.userId)
	Long billUser;

	Short billTable;

	LocalDateTime billCreatedAt;

	String billStatus;

//  Constructors begins here...
	public Bills() {

	}

	public Bills(Long billUser, Short billTable, String billStatus) {
		super();
		this.billUser = billUser;
		this.billTable = billTable;
		this.billStatus = billStatus;
	}

//  Getters Setters begins here...
	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public Long getBillUser() {
		return billUser;
	}

	public void setBillUser(Long billUser) {
		this.billUser = billUser;
	}

	public Short getBillTable() {
		return billTable;
	}

	public void setBillTable(Short billTable) {
		this.billTable = billTable;
	}

	public LocalDateTime getBillCreatedAt() {
		return billCreatedAt;
	}

	public void setBillCreatedAt(LocalDateTime billCreatedAt) {
		this.billCreatedAt = billCreatedAt;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

//  To String begins here...
	@Override
	public String toString() {
		return "Bills [billUser=" + billUser + ", billTable=" + billTable + ", billStatus=" + billStatus + "]";
	}

}
