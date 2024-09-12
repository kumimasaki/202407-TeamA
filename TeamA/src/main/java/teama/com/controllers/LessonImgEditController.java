package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import teama.com.models.entity.Admin;
import teama.com.models.entity.Lesson;
import teama.com.services.LessonService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LessonImgEditController {
	@Autowired
	private LessonService lessonService;

//	Sessionが使えるように宣言
	@Autowired
	private HttpSession session;
	
//	img編集画面を表示
	@GetMapping("/lesson/img/edit{imageName}")
	public String getImgEditPage(@PathVariable String imageName, Model model) {
//		セッションからloginしている人の情報をadminという変数に格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
//		もし、admin==null /admin/login画面にリダイレクトする
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
//			編集画面に表示させる情報を変数に格納 IMG
			Lesson img = lessonService.imageEditCheck(imageName);
//			もし、img=nullだったら、lesson編集ページにリダイレクトする			
//			そうでない場合img編集画面に編集する内容を渡す
//			img編集画面を表示
			if (img == null) {
				return "redirect:/lesson/edit";
			} else {
				model.addAttribute("imageName", img.getImageName());
				model.addAttribute("img", img);
				return "lesson_img_edit.html";
			}
		}
		
	}
	
	
	
	
	
	
	
	
}
