package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.websocket.Session;
import teama.com.models.entity.Users;
import teama.com.services.UserService;

@Controller
public class UserRegisterController {
	
	@Autowired
	private UserService userService;
	
	//登録画面の表示
	@GetMapping("/user/register")
	public String getUserRegisterPage() {
		return "userRegister.html";
	}
	
	// 登録処理
	@PostMapping("/user/register/process")
	public String userRegisterProcess(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String userPassword) {
		//登録成功だったら登録内容確認画面に進む
		//じゃないと登録画面に戻る
		if(userService.createUser(userName, userEmail, userPassword)) {
			return "user_Confirm.html";
		}else {
			return "userRegister.html";
		}
	}
	
	//登録確認画面の表示
	@GetMapping("/user/confirm")
	public String userConfirm(@ModelAttribute Users user, Model model) {
		model.addAttribute("user",user);
		return "user_confirm.html";
	}
	
	//　登録確認処理
	@PostMapping("/user/confirm/process")
	public String userConfirmProcess() {
		return "userLogin.html";
	}
	
    // 登録内容をリセットして、登録画面に戻るメソッド
    @PostMapping("/reset")
    public String resetRegistration(Model model) {
        model.addAttribute("user", new Users()); // 空のユーザーオブジェクトを渡す
        return "userRegister.html"; // 再度登録画面に戻る
    }

}
