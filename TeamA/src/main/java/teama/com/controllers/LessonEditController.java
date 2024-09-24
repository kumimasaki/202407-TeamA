package teama.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

@Controller
public class LessonEditController {
	@Autowired
	private LessonService lessonService;

	@Autowired
	private HttpSession session;

	// 編集画面の表示
	@GetMapping("/lesson/edit/{lessonId}")
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
			// そうでない場合、編集画面に編集する内容を渡す
			// 編集画面を表示
			if (lesson == null) {
				model.addAttribute("lesson", lesson);
				return "redirect:/lesson/list";
			} else {
				model.addAttribute("adminName", admin.getAdminName());
				model.addAttribute("lesson", lesson);
				return "lesson_edit.html";
			}
		}
	}

	// 更新処理をする
	@PostMapping("/lesson/edit/process")
	public String lessonUpdate(@RequestParam String imageName, @RequestParam String lessonName,
			@RequestParam int price, @RequestParam LocalDate startDate, @RequestParam LocalTime finishTime,
			@RequestParam LocalTime startTime, @RequestParam String description, @RequestParam Long lessonId,Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もし、admin == nullだったら、ログイン画面にリダイレクトする
		// そうでない場合
		// ファイルの保存
		// もし、lessonUpdateの結果がtrueの場合は、講座変更完了にリダイレクト
		// そうでない場合、講座編集画面にリダイレクトする
		if (admin == null) {
			model.addAttribute("adminName", admin.getAdminName());
			return "redirect:/admin/login";
		} else {
			if (lessonService.lessonUpdate(lessonId, startDate, startTime, finishTime, lessonName,description,  price,
					imageName, admin.getAdminId())) {
				return "lesson_edit_complete.html";
			} else {
				return "redirect:/lesson/edit";
			}
		}
	}
}
