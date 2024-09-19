package teama.com.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Users;
import teama.com.services.UserService;

@Controller
public class UserConfirmController {

	@Autowired
	private HttpSession session;

	@Autowired
	private UserService userService;

	// 確認画面を表示
	@PostMapping("/user/confirm")
	public String confirmRegister(@RequestParam("userName") String userName,
			@RequestParam("userEmail") String userEmail,
			@RequestParam("userPassword") String userPassword,
			Model model) {
		// ユーザー情報をモデルに追加して確認画面に表示
		model.addAttribute("userName", userName);
		model.addAttribute("useremail", userEmail);
		model.addAttribute("userpassword", userPassword);

		// 必要に応じて、ユーザー情報をセッションに一時保存
		Users user = new Users(userName, userEmail, userPassword);
		session.setAttribute("user", user);

		return "user_confirm.html"; // このページは確認情報のページ
	}

	// 登録確認後のロジックを処理し、ユーザー情報を保存
	@PostMapping("/user/register/check")
	public String completeRegister(Model model) {
		// セッションから一時ユーザー情報を取得
		Users user = (Users) session.getAttribute("user");

		if (user != null) {
			// ユーザー情報をデータベースに保存
			userService.saveUser(user);

			// セッション内の一時ユーザー情報を削除
			session.removeAttribute("user");

			// 成功画面、またはログイン画面にリダイレクト
			return "redirect:/user_login.html";
		} else
			return "redirect:/user_register.html"; // 登録画面に戻る
	}

	// 戻るボタンのロジックを処理し、登録画面に戻る
	@PostMapping("/user/register")
	public String backToRegister() {
		return "redirect:/user_register.html"; // 登録画面に戻る
	}
}
