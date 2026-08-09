package info.ogkapps.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Class Definition begins here...
@Entity
public class Stocks {

//  Fields begins here...	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long stockId;

//  This stockItem is a foreign key reference (Items.itemId)	
	Long stockItem;
	
	String stockName;
	
	String stockCatagory;
	
	String stockUnit;
	
	Integer stockCost;
	
	Integer stockCountLive;

//  This stockUser is a foreign key reference (Users.userId)	
	Long stockUser;
	
	LocalDateTime lastUpdated;
	
	String stockComments;

//  Constructors begins here...	
	public Stocks(Long stockItem, String stockName, Integer stockCountLive, Long stockUser) {
		super();
		this.stockItem = stockItem;
		this.stockName = stockName;
		this.stockCountLive = stockCountLive;
		this.stockUser = stockUser;
	}

//  Constructors begins here...	
	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getStockItem() {
		return stockItem;
	}

	public void setStockItem(Long stockItem) {
		this.stockItem = stockItem;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockCatagory() {
		return stockCatagory;
	}

	public void setStockCatagory(String stockCatagory) {
		this.stockCatagory = stockCatagory;
	}

	public String getStockUnit() {
		return stockUnit;
	}

	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}

	public Integer getStockCost() {
		return stockCost;
	}

	public void setStockCost(Integer stockCost) {
		this.stockCost = stockCost;
	}

	public Integer getStockCountLive() {
		return stockCountLive;
	}

	public void setStockCountLive(Integer stockCountLive) {
		this.stockCountLive = stockCountLive;
	}

	public Long getStockUser() {
		return stockUser;
	}

	public void setStockUser(Long stockUser) {
		this.stockUser = stockUser;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStockComments() {
		return stockComments;
	}

	public void setStockComments(String stockComments) {
		this.stockComments = stockComments;
	}

//  To String begins here...	
	@Override
	public String toString() {
		return "Stocks [stockItem=" + stockItem + ", stockName=" + stockName + ", stockCountLive=" + stockCountLive
				+ ", stockUser=" + stockUser + "]";
	}
	
}
