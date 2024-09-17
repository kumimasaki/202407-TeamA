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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Admin;
import teama.com.services.LessonService;
import teama.com.services.LessonServiceSou;

@Controller
public class LessonRegisterController {
	@Autowired
	private HttpSession session;

	@Autowired
	private LessonServiceSou lessonService;
	
	//講座画面の表示
	@GetMapping("/lesson/register")
	public String getLessonRegisterPage(Model model) {
		//セッションからログインしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		
		//もしadmin==null ログイン画面にリダイレクトする
		//そうでない場合は、ログインしている人の名前を画面に渡す
		//講座登録のhtmlを表示させる
		if(admin == null) {
			return "redirect:/admin/login";
		}else {
			model.addAttribute("adminName", admin.getAdminName());
			return "lesson_register.html";
		}
		
	}
	//講座の登録処理(html参照)
	@PostMapping("/lesson/register/process")
	public String lessonRegisterProcess(@RequestParam MultipartFile lessonImage,
			@RequestParam String lessonName,
			@RequestParam int price,
			@RequestParam LocalDate startDate,
			@RequestParam LocalDateTime endDate,
			@RequestParam LocalDateTime startTime,
			@RequestParam String description) {
		//セッションからログインしている人の情報をadminという変数に格納
	     Admin admin = (Admin) session.getAttribute("loginAdminInfo");
	   //もし、admin==nullだったら、ログイン画面にリダイレクトする
	     //そうでない場合は、画像のファイル名を取得
	     //画像のアップロード
	     //もし、同じファイルの名前がなかったら保存
	     //講座の一覧画面にリダイレクトする
	     //そうでない場合、商品登録画面にとどまります。
	     if(admin == null) {
	    	 return "redirect:/admin/login";
	     }else {
	    	//ファイルの名前を取得 
	    	 String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())+lessonImage.getOriginalFilename();
	    	//ファイルの保存作業
	    	 try {
					Files.copy(lessonImage.getInputStream(), Path.of("src/main/resources/static/lesson-img/"+fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	 //Serviceの講座の登録処理チェック参照
	    	 if(lessonService.createLesson(startDate, startTime,endDate,description, lessonName, price, fileName, admin.getAdminId())) {
	    		 return "redirect:/lesson/list";
	    	 }else{
	    		 return "lesson_register.html";
	     }
		
	}
}
}
