package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.services.AdminService;

@Controller
public class AdminLoginController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private HttpSession session;

	@GetMapping("/admin/login")
	public String getdAminLoginPage() {
		return "admin_login.html";
	}

	@PostMapping("/admin/login/process")
	public String postAdminLoginPage(@RequestParam String adminEmail, @RequestParam String adminPassword) {
		Admin admin = adminService.checkLogin(adminEmail, adminPassword);
		if (admin == null) {
			return "admin_login.html";
		} else {
			session.setAttribute("loginAdminInfo", admin);
			return "redirect:/lesson/list";
		}
	}
	
	
}