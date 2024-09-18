package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Admin_togyoho;
import teama.com.services.AdminService_togyoho;

@Controller
public class AdminRegisterController {
	@Autowired
	private AdminService_togyoho adminService_togyoho;

	@GetMapping("/admin/register")
	public String getRegisterPage() {
		return "admin_register.html";
	}

	@PostMapping("/admin/register/process")
	public String postRegisterPage(@RequestParam String adminName, @RequestParam String adminEmail,
			@RequestParam String adminPassword, @RequestParam String confirmAdminPassword) {
		//	Admin_togyoho admin_togyoho=adminService_togyoho.createAdmin(adminName, adminEmail,  adminPassword,confirmAdminPassword);
		if (adminPassword.equals(confirmAdminPassword)) {
			if (adminService_togyoho.createAdmin(adminName, adminEmail, adminPassword, confirmAdminPassword)) {
				return "admin_login.html";
			} else {
				return "admin_register.html";
			}
		} else {
			return "admin_register.html";
		}

	}
}
