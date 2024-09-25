package teama.com.controllers;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class LessonEditController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private LessonService lessonService;

	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	// 編集画面の表示
	@GetMapping("/lesson/edit/{lessonId}")
	// @PathVariable ブログIDを使ってデータベースからブログ情報を取得
	public String getLessonEditPage(@PathVariable Long lessonId, Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしadmin==null ログイン画面にリダイレクトする
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			// 編集画面に表示させる情報を変数に格納 lesson
			Lesson lesson = lessonService.lessonEditCheck(lessonId);
			// もしlesson == nullだったら、商品一覧ページにリダイレクトする

			if (lesson == null) {
				// adminnameを表示
				model.addAttribute("lesson", lesson);
				// 編集画面に編集する内容を渡す
				return "redirect:/lesson/list";
				// そうでない場合
			} else {
				// adminnameを表示
				model.addAttribute("adminName", admin.getAdminName());
				model.addAttribute("lesson", lesson);
				// 編集画面を表示
				return "lesson_edit.html";
			}
		}
	}

	// 更新処理をする
	@PostMapping("/lesson/edit/process")
	// @RequestParamはブラウザからのリクエストの値（パラメータ）を取得すること
	public String lessonUpdate(@RequestParam String imageName, @RequestParam String lessonName, @RequestParam int price,
			@RequestParam LocalDate startDate, @RequestParam LocalTime finishTime, @RequestParam LocalTime startTime,
			@RequestParam String description, @RequestParam Long lessonId, Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");

		// もし、admin == nullだったら、ログイン画面にリダイレクトする
		if (admin == null) {
			// adminnameを表示
			model.addAttribute("adminName", admin.getAdminName());
			return "redirect:/admin/login";
			// そうでない場合
		} else {
			// もし、lessonUpdateの結果がtrueの場合は
			if (lessonService.lessonUpdate(lessonId, startDate, startTime, finishTime, lessonName, description, price,
					imageName, admin.getAdminId())) {
				// adminnameを表示
				model.addAttribute("adminName", admin.getAdminName());
				// 講座変更完了画面に遷移する
				return "lesson_edit_complete.html";
				// そうでない場合
			} else {
				// 講座編集画面にリダイレクトする
				return "redirect:/lesson/edit";
			}
		}
	}
}
