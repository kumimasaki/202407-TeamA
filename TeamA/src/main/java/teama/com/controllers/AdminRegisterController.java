package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.services.AdminService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class AdminRegisterController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private AdminService adminService;

	// 登録画面の表示
	@GetMapping("/admin/register")
	public String getRegisterPage() {
		return "admin_register.html";
	}

	// 登録処理(登録画面から送信されたデータを受け取る)
	@PostMapping("/admin/register/process")
	// @RequestParamは、ブラウザからのリクエストの値（パラメータ）を取得することができるアノテーション。
	public String postRegisterPage(@RequestParam String adminName, @RequestParam String adminEmail,
			@RequestParam String adminPassword, @RequestParam String confirmAdminPassword) {
		if (adminPassword.equals(confirmAdminPassword)) {
			// もし、createAdminがtrue
			if (adminService.createAdmin(adminName, adminEmail, adminPassword, confirmAdminPassword)) {
				// ログイン画面に遷移
				return "admin_login.html";
				// そうでない場合
			} else {
				//登録画面にとどまります。
				return "admin_register.html";
			}
		} else {
			return "admin_register.html";
		}

	}
}
