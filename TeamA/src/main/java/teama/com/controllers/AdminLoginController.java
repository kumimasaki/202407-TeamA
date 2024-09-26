package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.services.AdminService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class AdminLoginController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private AdminService adminService;
	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	// ログイン画面の表示メソッド
	@GetMapping("/admin/login")
	public String getdAminLoginPage() {
		return "admin_login.html";
	}

	// ログイン処理(ログイン画面から送信されたデータを受け取る)
	@PostMapping("/admin/login/process")
	// @RequestParamは、ブラウザからのリクエストの値（パラメータ）を取得することができるアノテーション。
	public String postAdminLoginPage(@RequestParam String adminEmail, @RequestParam String adminPassword) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = adminService.checkLogin(adminEmail, adminPassword);
		// もし、adminの結果がtureの場合
		if (admin == null) {
			// ログイン画面を表示
			return "admin_login.html";
			// そうでない場合は
		} else {
			// sessionにログイン情報に保存
			session.setAttribute("loginAdminInfo", admin);
			// 講座一覧画面にリダイレクトする
			return "redirect:/lesson/list";
		}
	}

}