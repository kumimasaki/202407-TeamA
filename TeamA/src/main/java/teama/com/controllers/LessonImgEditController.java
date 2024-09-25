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

@Controller
public class LessonImgEditController {
	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private LessonDao lessonDao;

//	Sessionが使えるように宣言
	@Autowired
	private HttpSession session;

	// 変更画面で講座画像を変更ボタンを押したときの処理
	@GetMapping("/lesson/image/change/{lessonId}")
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
	public String imgUpdate(
			@RequestParam Long lessonId,
			@RequestParam LocalDate startDate,
			@RequestParam LocalTime startTime,
			@RequestParam LocalTime finishTime,
			@RequestParam String lessonName, 
			@RequestParam String lessonDetail, 
			@RequestParam int lessonFee,
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
	        Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	        Long adminId = admin.getAdminId();
	        lessonService.saveLesson(new Lesson(lessonId,startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, fileName, adminId));  // 保存 Lesson 实体
	        
	        return "lesson_edit_complete.html";  
	    } else {
	        model.addAttribute("errorMessage", "ファイルが選択されていません。");
	        return "lesson_img_edit.html";  // 如果没有选择文件，返回编辑页面并提示
	    }
	}
	

}
