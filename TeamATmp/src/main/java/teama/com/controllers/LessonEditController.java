package teama.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;

@Controller
public class LessonEditController {
//	class LessonServiceが使えるように宣言
	@Autowired
	private LessonService lessonService;

//	Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

//	編集画面を表示
	@GetMapping("/lesson/edit/{lessonId}")
	public String getLessonEditPage(@PathVariable Long lessonId, Model model) {
//		セッションからloginしている人の情報をadminという変数に格納
//		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
//		もし、admin==null /admin/login画面にリダイレクトする
//		if (admin == null) {
//			return "redirect:/admin/login";
//		} else {
//			編集画面に表示させる情報を変数に格納 lesson
			Lesson lesson = lessonService.lessonEditCheck(lessonId);
//			もし、lesson=nullだったら、lesson一覧ページにリダイレクトする			
//			そうでない場合lesson編集画面に編集する内容を渡す
//			lesson編集画面を表示
			if (lesson == null) {
				return "lesson_list.html";

//				return "redirect:/lesson/edit/{lessonId}";
			} else {
				model.addAttribute("lessonId", lesson.getLessonId());
				model.addAttribute("lesson", lesson);
				return "lesson_edit.html";
			}
			
//		}
		
	}
	
//	更新処理をする
	@PostMapping("/lesson/edit/process")
	public String lessonUpdate(
			@RequestParam Long lessonId,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDateTime startTime,
			@RequestParam LocalDateTime finishTime,
			@RequestParam String lessonName,
			@RequestParam String lessonDetail,
			@RequestParam int lessonFee,
			@RequestParam String imageName,
			@RequestParam Long adminId
			) {
//		セッションからloginしている人の情報をadminという変数に格納
//		Admin admin = (Admin) session.getAttribute("loginAdminInfo");

//		もし、admin==null edit画面にリダイレクトする
//		そうでない場合、
//		もし、lessonUpdate の結果がtrueの場合、/lesson/listにリダイレクトする
//		そうでない場合、/lesson/edit編集画面にリダイレクト
//		if (admin == null) {
//			return "redirect:/admin/login";
//		} else {
//			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
//					+ lessonId.getOriginalFilename();
//			try {
//				Files.copy(lessonId.getInputStream(), Path.of("src/main/resources/static/lesson-img/" + fileName));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		if (lessonService.lessonUpdate(lessonId, startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName, admin.getAdminId())) {
			if (lessonService.lessonUpdate(lessonId, startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName, adminId)) {
				return "lesson_list.html";
//						"redirect:/lesson/edit";
			} else {
				return "lesson_edit.html";
//						"redirect:/lesson/img/edit" + imageName;

			}

		}
//	}
	
//	img編集画面を表示
	@GetMapping("/lesson/img/edit/{imageName}")
	public String getImgEditPage(@PathVariable String imageName, Model model) {
//		セッションからloginしている人の情報をadminという変数に格納
//		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
//		もし、admin==null /admin/login画面にリダイレクトする
//		if (admin == null) {
//			return "redirect:/admin/login";
//		} else {
//			IMG編集画面に表示させる情報を変数に格納 IMG
			Lesson img = lessonService.imageEditCheck(imageName);
//			もし、img=nullだったら、lesson編集ページにリダイレクトする			
//			そうでない場合img編集画面に編集する内容を渡す
//			img編集画面を表示
			if (img == null) {
				return "lesson_img_edit.html";

//				return "redirect:/lesson/img/edit/{imageName}";
			} else {
				model.addAttribute("imageName", img.getImageName());
				model.addAttribute("img", img);
				return "lesson_img_edit.html";
			}
			
//		}
		
	}
	
//	更新処理をする
	@PostMapping("/lesson/img/edit/process")
	public String imgUpdate(
			@RequestParam String imageName) {
//		セッションからloginしている人の情報をadminという変数に格納
//		Admin admin = (Admin) session.getAttribute("loginAdminInfo");

//		もし、admin==null edit画面にリダイレクトする
//		そうでない場合、
		/*
		 * 現在の日時情報をもとに、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
		 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
		 * その後、imageNameオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入
		 */
//		ファイルの保存
//		もし、imageUpdate の結果がtrueの場合、/lesson/editにリダイレクトする
//		そうでない場合、/lesson/img/edit編集画面にリダイレクト
//		if (admin == null) {
//			return "redirect:/admin/login";
//		} else {
//			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
//					+ imageName.getOriginalFilename();
//			try {
//				Files.copy(imageName.getInputStream(), Path.of("src/main/resources/static/lesson-img/" + fileName));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (lessonService.imageUpdate(imageName)) {
				return "lesson_edit.html";
//						"redirect:/lesson/edit";
			} else {
				return "lesson_img_edit.html";
//						"redirect:/lesson/img/edit" + imageName;

			}

		}
//	}
	
}
