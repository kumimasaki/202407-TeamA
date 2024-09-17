package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
