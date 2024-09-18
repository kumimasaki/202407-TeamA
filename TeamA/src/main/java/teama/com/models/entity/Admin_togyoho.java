package teama.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin_togyoho {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminId;

	private String adminName;
	private String adminEmail;
	private String adminPassword;
	private String adminConfirmPassword;
	
	public Admin_togyoho() {
	}

	public Admin_togyoho(String adminName, String adminEmail, String adminPassword, String adminConfirmPassword) {
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.adminConfirmPassword = adminConfirmPassword;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminConfirmPassword() {
		return adminConfirmPassword;
	}

	public void setAdminConfirmPassword(String adminConfirmPassword) {
		this.adminConfirmPassword = adminConfirmPassword;
	}
	
	
	

}