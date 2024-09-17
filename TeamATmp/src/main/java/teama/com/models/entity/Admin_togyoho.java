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
	private String adminReenterPassword;
	public Admin_togyoho() {
	}
	public Admin_togyoho(String adminName, String adminEmail, String adminPassword, String adminReenterPassword) {
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.adminReenterPassword = adminReenterPassword;
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
	public String getAdminReenterPassword() {
		return adminReenterPassword;
	}
	public void setAdminReenterPassword(String adminReenterPassword) {
		this.adminReenterPassword = adminReenterPassword;
	}
	
	

}