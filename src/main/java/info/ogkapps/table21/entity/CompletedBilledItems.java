package info.ogkapps.table21.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CompletedBilledItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long cbiId;
	
	// foreign key reference Bills.BillId
	Long cbiBillId;

	String cbiCode;

	String cbiName;

	Integer cbiQuantity;

	Integer cbiCost;

	Short cbiGst;

	public CompletedBilledItems(Long cbiBillId, String cbiCode, String cbiName, Integer cbiQuantity, Integer cbiCost,
			Short cbiGst) {
		super();
		this.cbiBillId = cbiBillId;
		this.cbiCode = cbiCode;
		this.cbiName = cbiName;
		this.cbiQuantity = cbiQuantity;
		this.cbiCost = cbiCost;
		this.cbiGst = cbiGst;
	}

	public Long getCbiId() {
		return cbiId;
	}

	public void setCbiId(Long cbiId) {
		this.cbiId = cbiId;
	}

	public Long getCbiBillId() {
		return cbiBillId;
	}

	public void setCbiBillId(Long cbiBillId) {
		this.cbiBillId = cbiBillId;
	}

	public String getCbiCode() {
		return cbiCode;
	}

	public void setCbiCode(String cbiCode) {
		this.cbiCode = cbiCode;
	}

	public String getCbiName() {
		return cbiName;
	}

	public void setCbiName(String cbiName) {
		this.cbiName = cbiName;
	}

	public Integer getCbiQuantity() {
		return cbiQuantity;
	}

	public void setCbiQuantity(Integer cbiQuantity) {
		this.cbiQuantity = cbiQuantity;
	}

	public Integer getCbiCost() {
		return cbiCost;
	}

	public void setCbiCost(Integer cbiCost) {
		this.cbiCost = cbiCost;
	}

	public Short getCbiGst() {
		return cbiGst;
	}

	public void setCbiGst(Short cbiGst) {
		this.cbiGst = cbiGst;
	}

	@Override
	public String toString() {
		return "CompletedBilledItems [cbiId=" + cbiId + ", cbiBillId=" + cbiBillId + ", cbiCode=" + cbiCode
				+ ", cbiName=" + cbiName + ", cbiQuantity=" + cbiQuantity + ", cbiCost=" + cbiCost + ", cbiGst="
				+ cbiGst + "]";
	}
}
