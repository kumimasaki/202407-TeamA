package teama.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

@Controller
public class LessonListController {
	@Autowired
	private HttpSession session;

	@Autowired
	private LessonService lessonService;

	// 講座一覧画面を表示する
	@GetMapping("/lesson/list")
	public String getLessonList(Model model) {
		// セッションからログインしている人の情報を取得
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もし、admin==null ログイン画面にリダイレクトする
		// そうでない場合
		// ログインしている人の名前の情報を画面に渡して講座一覧のhtmlを表示。
		if (admin == null) {
			model.addAttribute("adminName", admin.getAdminName());
			return "redirect:/admin/login";
		} else {
			// 講座の情報を取得する。
			List<Lesson> lessonList = lessonService.selectAllLessonList(admin.getAdminId());
			model.addAttribute("adminName", admin.getAdminName());
			model.addAttribute("lessonList", lessonList);
			return "lesson_list.html";
		}
	}
}
