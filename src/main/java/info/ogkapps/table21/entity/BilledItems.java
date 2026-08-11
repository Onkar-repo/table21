package info.ogkapps.table21.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//  Class Definition begins here...
@Entity
public class BilledItems {

//  Fields begins here...
	
//  Unnecessary column, added only to fulfill mapping requirement based on (primary key field required)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long BilledItemPK;
	
//  This billedItemParent is a foreign key reference (Bills.billId)
	Long billedItemParent;

	Short billedItemSerial;

	Integer billedItemQuantity;

//  This billedItemIdentity is a foreign key reference (Items.itemId)
	Long billedItemIdentity;

//  Constructors begins here...
	public BilledItems(Long billedItemParent, Short billedItemSerial, Integer billedItemQuantity,
			Long billedItemIdentity) {
		super();
		this.billedItemParent = billedItemParent;
		this.billedItemSerial = billedItemSerial;
		this.billedItemQuantity = billedItemQuantity;
		this.billedItemIdentity = billedItemIdentity;
	}

//  Getters Setters begins here...
	public Long getBilledItemParent() {
		return billedItemParent;
	}

	public void setBilledItemParent(Long billedItemParent) {
		this.billedItemParent = billedItemParent;
	}

	public Short getBilledItemSerial() {
		return billedItemSerial;
	}

	public void setBilledItemSerial(Short billedItemSerial) {
		this.billedItemSerial = billedItemSerial;
	}

	public Integer getBilledItemQuantity() {
		return billedItemQuantity;
	}

	public void setBilledItemQuantity(Integer billedItemQuantity) {
		this.billedItemQuantity = billedItemQuantity;
	}

	public Long getBilledItemIdentity() {
		return billedItemIdentity;
	}

	public void setBilledItemIdentity(Long billedItemIdentity) {
		this.billedItemIdentity = billedItemIdentity;
	}

	public Long getBilledItemPK() {
		return BilledItemPK;
	}

	public void setBilledItemPK(Long billedItemPK) {
		BilledItemPK = billedItemPK;
	}

	//  To String begins here...
	@Override
	public String toString() {
		return "BilledItems [billedItemParent=" + billedItemParent + ", billedItemSerial=" + billedItemSerial
				+ ", billedItemQuantity=" + billedItemQuantity + ", billedItemIdentity=" + billedItemIdentity + "]";
	}

}
