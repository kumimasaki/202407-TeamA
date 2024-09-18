package teama.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.AdminDao_togyoho;
import teama.com.models.entity.Admin_togyoho;

@Service
public class AdminService_togyoho {
@Autowired
private AdminDao_togyoho adminDao_togyoho; 

public boolean createAdmin(String adminName, String adminEmail, String adminPassword, String adminConfirmPassword) {
	if(adminDao_togyoho.findByadminEmail(adminEmail)==null) {
		adminDao_togyoho.save(new Admin_togyoho(adminName,adminEmail,adminPassword, adminConfirmPassword));
		return true;
	}else {
		return false;
	}
}

public Admin_togyoho checkLogin(String adminEmail,String adminPassword) {
	Admin_togyoho admin_togyoho =adminDao_togyoho.findByadminEmailAndAdminPassword(adminEmail, adminPassword);
	if(admin_togyoho==null) {
		return null;
	}else {
		return admin_togyoho;
	}
}
public  Admin_togyoho checkPassword(String AdminConfirmPassword,String adminPassword) {
	Admin_togyoho admin_togyoho=adminDao_togyoho.findByAdminPasswordAndAdminConfirmPassword(adminPassword, AdminConfirmPassword);
	if(admin_togyoho==null) {
		return null;
		}else {
			return admin_togyoho;
		}
	}
}

