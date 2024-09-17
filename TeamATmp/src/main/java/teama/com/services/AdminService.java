package teama.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.AdminDao;
import teama.com.models.entity.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public Admin checkLogin(String adminEmail, String password) {
		Admin admin = adminDao.findByAdminEmailAndAdminPassword(adminEmail, password);
		if (admin == null) {
			return null;
		} else {
			return admin;
		}

	}

}
