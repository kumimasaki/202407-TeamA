package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Users;
import teama.com.services.UserService;

@Controller
public class UserLoginController {
	@Autowired
	private UserService userService;
	
	//Sessionが使えるように宣言
	@Autowired
	private HttpSession session;
	
	//ログイン画面の表示
	@GetMapping("/user/login")
	public String getUserLoginPage(Model model) {
		return "userLogin.html";
	}
	
	//ログイン処理
	@PostMapping("/user/login/process")
	public ModelAndView userLoginProcess(@RequestParam String userEmail, @RequestParam String userPassword, ModelAndView mav ) {
		//loginCheckメソッド呼び出し
		Users user = userService.loginCheck(userEmail, userPassword);
		if(user == null) {
			mav.setViewName("userLogin.html");
			return mav;
		}else {
			session.setAttribute("loginUserInfo", user);
			mav.addObject("userEmail",userEmail);
			mav.addObject("userPassword",userPassword);
			mav.setViewName("redirect:/user/lesson/list");
			return mav;
		}
	}
}
