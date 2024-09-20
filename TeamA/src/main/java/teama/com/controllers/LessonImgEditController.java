package teama.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import jakarta.servlet.http.HttpSession;

@Controller
public class LessonImgEditController {
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private LessonDao lessonDao;

//	Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

//	img編集画面を表示
	@GetMapping("/lesson/img/edit/{imageName}")
	public String getImgEditPage(@PathVariable String imageName, Model model) {
//		セッションからloginしている人の情報をadminという変数に格納
//		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
////		もし、admin==null /admin/login画面にリダイレクトする
//		if (admin == null) {
//			return "redirect:/admin/login";
//		} else {
//			編集画面に表示させる情報を変数に格納 IMG
		Lesson img = lessonService.imageEditCheck(imageName);
//			もし、img=nullだったら、lesson編集ページにリダイレクトする			
//			そうでない場合img編集画面に編集する内容を渡す
//			img編集画面を表示
		if (img == null) {
			return "lesson_img_edit.html";
//				return "redirect:/lesson/edit";
		} else {
			model.addAttribute("imageName", img.getImageName());
			model.addAttribute("img", img);
			return "lesson_img_edit.html";
		}
	}

//	}

	// 更新処理をする
//	@PostMapping("/lesson/img/edit/process")
//	public String imgUpdate(
//			@RequestParam String imageName
//			) {
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
//			if (lessonService.imageUpdate(imageName)) {
//				return "lesson_edit.html";
////						"redirect:/lesson/edit";
//			} else {
//				return "lesson_img_edit.html";
////						"redirect:/lesson/img/edit" + imageName;
//
//			}
//
//		}
//	}

//	
//	@PostMapping("/lesson/img/edit/process")
//	public String imgUpdate(@RequestParam("imageFile ") MultipartFile imageFile) {
//
//		// 确保上传了文件
//		if (!imageFile.isEmpty()) {
//			// 生成带有时间戳的文件名
//			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
//					+ imageFile.getOriginalFilename();
//
//			try {
//				// 将文件保存到服务器指定路径
//				Path uploadPath = Path.of("src/main/resources/static/lesson-img/" + fileName);
//				Files.copy(imageFile.getInputStream(), uploadPath);
//
//				// 将文件名保存到数据库
//				Lesson lesson = new Lesson();
//				lesson.setImageName(fileName); // 保存文件名
//				lessonService.saveLesson(lesson); // 假设你有一个保存 Lesson 的方法
//
//			} catch (IOException e) {
//				e.printStackTrace();
//				return "lesson_img_edit.html"; // 发生错误时返回图片编辑页面
//			}
//
//			return "redirect:/lesson/edit"; // 文件上传成功，重定向到 lesson 编辑页面
//		} else {
//			// 如果没有上传文件
//			return "lesson_img_edit.html"; // 没有上传文件时仍然返回图片编辑页面
//		}
//	}

	@PostMapping("/lesson/img/edit/process")
	public String imgUpdate(
			@RequestParam Long lessonId,
			@RequestParam LocalDate startDate,
//			@RequestParam LocalDateTime startTime,
//			@RequestParam LocalDateTime finishTime,
			@RequestParam String lessonName, @RequestParam String lessonDetail, @RequestParam int lessonFee,
			@RequestParam("imageFile") MultipartFile imageFile,
			Model model) {
	    if (!imageFile.isEmpty()) {
	        // 生成带有时间戳的文件名
	        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
	                + imageFile.getOriginalFilename();
	        try {
	            // 将文件保存到服务器指定路径
	            Path uploadPath = Path.of("src/main/resources/static/lesson-img/" + fileName);
	            Files.copy(imageFile.getInputStream(), uploadPath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            model.addAttribute("errorMessage", "ファイルの保存中にエラーが発生しました。");
	            return "lesson_img_edit.html";  // 出现错误时，仍然返回编辑页面并显示错误信息
	        }
//	        // 更新数据库中的 imageName
//	        Lesson lesson = new Lesson();
//	        // 暂时使用默认的 adminId，例如 1
//	        lesson.setAdminId((long) 1);  // 这里设置一个默认的 adminId
//	        lesson.setLessonId((long) 1);
//	        // 保存图片文件并设置 imageName
//	        lesson.setImageName(saveImage(imageFile));
//	        lesson.setImageName(fileName);  // 保存文件名到数据库
	        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	        Long adminId = admin.getAdminId();
	        LocalTime time = LocalTime.of(0,0,0);
	        lessonService.saveLesson(new Lesson(lessonId,startDate, time, time, lessonName, lessonDetail, lessonFee, fileName, adminId));  // 保存 Lesson 实体
	        
	        // 保存成功后，将新的 imageName 返回到页面
	        model.addAttribute("imageName", fileName);
	        model.addAttribute("lessonId", lessonId);
	        return "lesson_edit.html";  // 上传成功后回到图片编辑页面并显示新图片
	    } else {
	        model.addAttribute("errorMessage", "ファイルが選択されていません。");
	        return "lesson_img_edit.html";  // 如果没有选择文件，返回编辑页面并提示
	    }
	}
	
	@GetMapping("/lesson/image/change/{lessonId}")
	public String getLessonImageEditPage(@PathVariable Long lessonId, Model model) {
		// 現在ログインしている管理者情報を取得する
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// 現在ログインしている人の名前を取得する
		String loginAdminName = admin.getAdminName();
		Lesson lessonList = lessonDao.findByLessonId(lessonId);
		model.addAttribute("adminName", loginAdminName);
		model.addAttribute("lessonList", lessonList);
		return "lesson_img_edit.html";
	}

	private String saveImage(MultipartFile imageFile) {
		// TODO Auto-generated method stub
		return null;
	}

}
