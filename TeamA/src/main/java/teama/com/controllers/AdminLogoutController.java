package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLogoutController {
@Autowired
 private HttpSession session;

@GetMapping("/admin/logout")
public String getLogoutPage() {
	session.invalidate();
	return "redirect:/admin/login";
}
}
