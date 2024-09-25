package teama.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class LessonListController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	@Autowired
	private LessonService lessonService;

	// 講座一覧画面を表示する
	@GetMapping("/lesson/list")
	public String getLessonList(Model model) {
		// セッションからログインしている人の情報を取得
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もし、admin==null
		if (admin == null) {
			model.addAttribute("adminName", admin.getAdminName());
			// ログイン画面にリダイレクトする
			return "redirect:/admin/login";
			// そうでない場合
		} else {
			// 講座の情報を取得する。
			List<Lesson> lessonList = lessonService.selectAllLessonList(admin.getAdminId());
			// ユーザー名前を表示
			model.addAttribute("adminName", admin.getAdminName());
			model.addAttribute("lessonList", lessonList);
			// ログインしている人の名前の情報を画面に渡して講座一覧のhtmlを表示
			return "lesson_list.html";
		}
	}
}
