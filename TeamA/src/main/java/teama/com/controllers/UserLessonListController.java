package teama.com.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Lesson;
import teama.com.models.entity.Users;
import teama.com.services.LessonServiceShao;

@Controller
public class UserLessonListController {

	@Autowired
	private LessonServiceShao lessonServiceShao;

	@Autowired
	private HttpSession session;

	// 1.コース一覧ページの表示
	@GetMapping("/user/lesson/list")
	public String getLessonList(Model model) {
		
		// セッションからユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUserInfo");

		// ユーザーがログインしていない場合、ログインページにリダイレクト
		if (user == null) {
			return "redirect:/user/login"; 
		} else {
			// serviceからすべての情報を取得
			List<Lesson> lessonList = lessonServiceShao.selectAllLessonList(user.getUserId()); //adminid？
			model.addAttribute("lessonList", lessonList);
			model.addAttribute("username", user.getUserName());
			return "user_cart_list.html";
		}
	}

	//  2.カートに追加
	@PostMapping("/cart/list")
	public String addCartPage(@RequestParam Long lessonId, Model model) {
		Users user = (Users) session.getAttribute("loginUserInfo");

		if (session.getAttribute("cart") == null) {
			LinkedList<Lesson> list = new LinkedList<Lesson>();
			Lesson lesson = lessonServiceShao.findByLessonId(lessonId);
			list.add(lesson);
			session.setAttribute("cart", list);
			// ユーザーがログインしていない場合、ログインページにリダイレクト
			if (user == null) {
				return "redirect:/user/login"; 
			} else {
				model.addAttribute("list", list);
				model.addAttribute("userName", user.getUserName());
				return "user_cart_list.html";
			}
		} else {
			LinkedList<Lesson> list = (LinkedList<Lesson>) session.getAttribute("cart");
			Lesson lesson = lessonServiceShao.findByLessonId(lessonId);
			
			// カートに同じ講座があるかどうかチェック
			if (isLessonExist(lessonId, list)) {
				// すでに存在している場合、何もしない
			} else {
				list.add(lesson);
			}
			
			model.addAttribute("list", list);
			model.addAttribute("userName", user.getUserName());
			return "user_cart_list.html";
		}
	}
	
	// 3.カートに同じ講座があるかどうかチェック
	public boolean isLessonExist(Long lessonId, LinkedList<Lesson> list) {
		Iterator<Lesson> ite = list.iterator();
		boolean isExist = false;
		while (ite.hasNext()) {
			Lesson lesson = ite.next();
			if (lesson.getLessonId().equals(lessonId)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}
}
