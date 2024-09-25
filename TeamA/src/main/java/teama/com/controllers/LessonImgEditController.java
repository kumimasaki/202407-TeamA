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

import teama.com.models.dao.LessonDao;
import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

//@Controllerアノテーションの主な役割は、主にHTMLページなどのビューを生成すること(司令塔)
@Controller
public class LessonImgEditController {
	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private LessonService lessonService;

	@Autowired
	private LessonDao lessonDao;

	@Autowired
	// sessionは、ユーザー情報を、一時的にサーバー側で保持される、 どのページも共通もデータを使いたい時にもちいる機能、（名前、値）という形式で保管される
	private HttpSession session;

	// 変更画面で講座画像を変更ボタンを押したときの処理
	@GetMapping("/lesson/image/change/{lessonId}")
	// @PathVariable ブログIDを使ってデータベースからブログ情報を取得
	public String getLessonImageEditPage(@PathVariable Long lessonId, Model model) {
		// 現在ログインしている管理者情報を取得する
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// 現在ログインしている人の名前を取得する
		String adminName = admin.getAdminName();
		Lesson lesson = lessonDao.findByLessonId(lessonId);
		model.addAttribute("adminName", adminName);
		model.addAttribute("lesson", lesson);
		return "lesson_img_edit.html";
	}

	// 講座画面の変更で変更ボタンを押したときの処理
	@PostMapping("/lesson/img/edit/process")
	// @PathVariable ブログIDを使ってデータベースからブログ情報を取得
	public String imgUpdate(@RequestParam Long lessonId, @RequestParam LocalDate startDate,
			@RequestParam LocalTime startTime, @RequestParam LocalTime finishTime, @RequestParam String lessonName,
			@RequestParam String lessonDetail, @RequestParam int lessonFee,
			@RequestParam("imageFile") MultipartFile imageFile, Model model) {
		if (!imageFile.isEmpty()) {
			// ファイルの名前を取得
			/**
			 * 現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
			 **/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ imageFile.getOriginalFilename();
			try {
				// ファイルの保存作業
				Path uploadPath = Path.of("src/main/resources/static/lesson-img/" + fileName);
				Files.copy(imageFile.getInputStream(), uploadPath);
			} catch (IOException e) {
				e.printStackTrace();
				// エラーがあり時、エラーメッセージを表す
				model.addAttribute("errorMessage", "ファイルの保存中にエラーが発生しました。");
				// 画像編集を遷移する
				return "lesson_img_edit.html";
			}
			Admin admin = (Admin) session.getAttribute("loginAdminInfo");
			Long adminId = admin.getAdminId();
			lessonService.saveLesson(new Lesson(lessonId, startDate, startTime, finishTime, lessonName, lessonDetail,
					lessonFee, fileName, adminId)); // 保存 Lesson 实体
			// 画像編集完了を遷移する
			return "lesson_edit_complete.html";
		} else {
			// エラーがあり時、エラーメッセージを表す
			model.addAttribute("errorMessage", "ファイルが選択されていません。");
			// 画像編集を遷移する
			return "lesson_img_edit.html";
		}
	}

}
