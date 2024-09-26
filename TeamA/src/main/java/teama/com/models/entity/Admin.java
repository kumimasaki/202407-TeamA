package teama.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
	// admin_id設定
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long adminId;

	// admin_name設定
	private String adminName;

	// admin_email設定
	private String adminEmail;

	// password設定
	private String adminPassword;

	// adminConfirmPassword設定
	private String adminConfirmPassword;

	// 空のコンストラク作成
	public Admin() {
	}

	// ALLコンストラク作成
	public Admin(String adminName, String adminEmail, String adminPassword, String adminConfirmPassword) {
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
		this.adminConfirmPassword = adminConfirmPassword;
	}

	// get,set作成
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