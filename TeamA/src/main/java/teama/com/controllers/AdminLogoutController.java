package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class AdminLogoutController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	//sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	// ログアウト処理ログアウト画面の表示
	@GetMapping("/admin/logout")
	public String getLogoutPage() {
		// ログアウトの時Loginの情報を残らないように、セッションの無効化します
		session.invalidate();
		return "redirect:/admin/login";
	}
}
