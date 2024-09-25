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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import teama.com.models.entity.Admin;
import teama.com.services.LessonService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class LessonRegisterController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	@Autowired
	private LessonService lessonService;

	// 講座画面の表示
	@GetMapping("/lesson/register")
	public String getLessonRegisterPage(Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");

		// もしadmin==null
		if (admin == null) {
			// ユーザー名前を表示
			model.addAttribute("adminName", admin.getAdminName());
			// ログイン画面にリダイレクトする
			return "redirect:/admin/login";
			// そうでない場合は、ログインしている人の名前を画面に渡す
		} else {
			// ユーザー名前を表示
			model.addAttribute("adminName", admin.getAdminName());
			// 講座登録のhtmlを表示させる
			return "lesson_register.html";
		}

	}

	// 講座の登録処理(html参照)
	@PostMapping("/lesson/register/process")
	// @RequestParamはブラウザからのリクエストの値（パラメータ）を取得すること
	public String lessonRegisterProcess(@RequestParam MultipartFile lessonImage, @RequestParam String lessonName,
			@RequestParam int price, @RequestParam LocalDate startDate, @RequestParam LocalTime finishTime,
			@RequestParam LocalTime startTime, @RequestParam String description, Model model) {
		// セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もし、admin==nullだったら
		if (admin == null) {

			// ログイン画面にリダイレクトする
			return "redirect:/admin/login";
			// そうでない場合は
		} else {
			// 画像のファイルの名前を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ lessonImage.getOriginalFilename();
			// ファイルの保存作業
			try {
				// 画像のアップロード
				Files.copy(lessonImage.getInputStream(), Path.of("src/main/resources/static/lesson-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Serviceの講座の登録処理チェック参照
			// もし、同じファイルの名前がなかったら保存
			if (lessonService.createLesson(startDate, startTime, finishTime, description, lessonName, price, fileName,
					admin.getAdminId())) {
				// ユーザー名前を表示
				model.addAttribute("adminName", admin.getAdminName());
				// 講座登録完了にリダイレクトする
				return "lesson_register_complete.html";
				// そうでない場合
			} else {
				// 講座登録画面にとどまります。
				return "lesson_register.html";
			}

		}
	}
}
