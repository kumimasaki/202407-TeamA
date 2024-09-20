package teama.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.AdminDao;
import teama.com.models.entity.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public boolean createAdmin(String adminName, String adminEmail, String adminPassword, String adminConfirmPassword) {
		if (adminDao.findByAdminEmail(adminEmail) == null) {
			adminDao.save(new Admin(adminName, adminEmail, adminPassword, adminConfirmPassword));
			return true;
		} else {
			return false;
		}
	}

	public Admin checkLogin(String adminEmail, String adminPassword) {
		Admin admin_togyoho = adminDao.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
		if (admin_togyoho == null) {
			return null;
		} else {
			return admin_togyoho;
		}
	}

	public Admin checkPassword(String AdminConfirmPassword, String adminPassword) {
		Admin admin_togyoho = adminDao.findByAdminPasswordAndAdminConfirmPassword(adminPassword, AdminConfirmPassword);
		if (admin_togyoho == null) {
			return null;
		} else {
			return admin_togyoho;
		}
	}
}
