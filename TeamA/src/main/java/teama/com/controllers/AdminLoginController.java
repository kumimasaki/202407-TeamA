package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin_togyoho;
import teama.com.services.AdminService_togyoho;

@Controller
public class AdminLoginController {
@Autowired
private AdminService_togyoho adminService_togyoho;
@Autowired
private HttpSession session;

@GetMapping("/admin/login")
public String getAdminLoginPage() {
	return "admin_login.html";
} 

@PostMapping("/admin/login/process")
public String postAdminLoginPage(@RequestParam String adminEmail,
		                         @RequestParam String adminPassword) {
	Admin_togyoho admin_togyoho=adminService_togyoho.checkLogin(adminEmail, adminPassword);
	if(admin_togyoho ==null) {
		return "admin_login.html";
	}else {
		session.setAttribute("adminInfo", admin_togyoho);
		return "redirect:/lesson/list";
	}
}
}
