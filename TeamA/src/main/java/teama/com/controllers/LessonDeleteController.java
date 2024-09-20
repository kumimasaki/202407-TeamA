package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.services.LessonService;


@Controller
public class LessonDeleteController {
	@Autowired
	private LessonService lessonService;

	@Autowired
	private HttpSession session;

	@GetMapping("/lesson/delete/{lessonId}")
	public String deleteByLesson(@PathVariable Long lessonId) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしadmin==null ログイン画面にリダイレクトする
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			// もしdeleteByLessonの結果がtrueの時に、lesson_delete_complete.htmlを遷移する
			// そうでない場合、 編集画面にリダイレクトする
			if (lessonService.deleteByLesson(lessonId)) {
				return "lesson_delete_complete.html";
			} else {
				return "redirect:/lesson/edit/" + lessonId;
			}
		}
	}

}
