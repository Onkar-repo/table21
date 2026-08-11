package info.ogkapps.table21.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//  Class Definition begins here...
@Entity
public class Users {

//  Fields begins here...
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long userId;
	
	String userName;
	
	String userEmail;
	
	String userPassword;
	
	LocalDateTime createdAt;

//  Constructors begins here...
	public Users() {

	}

	public Users(String userName, String userEmail, String userPassword) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

//  Getters Setters begins here...
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

//  To String begins here...
	@Override
	public String toString() {
		return "Users [userName=" + userName + ", userEmail=" + userEmail + "]";
	}

}
