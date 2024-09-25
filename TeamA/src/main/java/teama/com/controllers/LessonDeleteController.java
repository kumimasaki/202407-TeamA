package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.services.LessonService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class LessonDeleteController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private LessonService lessonService;

	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	// 削除画面の表示
	@GetMapping("/lesson/delete/{lessonId}")
	// @PathVariable ブログIDを使ってデータベースからブログ情報を取得
	public String deleteByLesson(@PathVariable Long lessonId, Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしadmin==null ログイン画面にリダイレクトする
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			// もしdeleteByLessonの結果がtrueの場合
			if (lessonService.deleteByLesson(lessonId)) {
				// ユーザー名前を表示
				model.addAttribute("adminName", admin.getAdminName());
				// 削除完了画面を遷移する
				return "lesson_delete_complete.html";
				// そうでない場合
			} else {
				// adminnameを表示
				model.addAttribute("adminName", admin.getAdminName());
				// 編集画面にリダイレクトする
				return "redirect:/lesson/edit/" + lessonId;
			}
		}
	}

}
