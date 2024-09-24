package teama.com.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Lesson;
import teama.com.models.entity.Users;
import teama.com.services.LessonService;

@Controller
public class UserLessonListController {

	@Autowired
	private LessonService lessonService;

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
			List<Lesson> lessonList = lessonService.selectAllLessonListShao();
			model.addAttribute("lessonList", lessonList);
			model.addAttribute("username", user.getUserName());
			return "user_lesson_list.html";
		}
	}
	
	// 1.コース一覧ページの表示
	@GetMapping("/view/cart/list")
	public String displayCartPage(Model model) {
		Users user = (Users) session.getAttribute("loginUserInfo");
		// セッションからカート情報を取得
		LinkedList<Lesson> list = (LinkedList<Lesson>) session.getAttribute("cart");

		int total = calculateTotalPrice(list);
		model.addAttribute("total", total);
		model.addAttribute("lessonList", list);
		model.addAttribute("userName", user.getUserName());
		return "user_cart_list.html";
	}

	//  2.カートに追加
	@PostMapping("/cart/list")
	public String addCartPage(@RequestParam Long lessonId, Model model) {
		Users user = (Users) session.getAttribute("loginUserInfo");

		if (session.getAttribute("cart") == null) {
			LinkedList<Lesson> list = new LinkedList<Lesson>();
			Lesson lesson = lessonService.findByLessonId(lessonId);
			list.add(lesson);
			session.setAttribute("cart", list);
			// ユーザーがログインしていない場合、ログインページにリダイレクト
			if (user == null) {
				return "redirect:/user/login"; 
			} else {
				int total = calculateTotalPrice(list);
				model.addAttribute("total", total);
				model.addAttribute("lessonList", list);
				model.addAttribute("userName", user.getUserName());
				return "user_cart_list.html";
			}
		} else {
			LinkedList<Lesson> list = (LinkedList<Lesson>) session.getAttribute("cart");
			Lesson lesson = lessonService.findByLessonId(lessonId);

			// カートに同じ講座があるかどうかチェック
			if (isLessonExist(lessonId, list)) {
				// すでに存在している場合、何もしない
			} else {
				list.add(lesson);
			}

			int total = calculateTotalPrice(list);
			model.addAttribute("total", total);
			model.addAttribute("lessonList", list);
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

	// カート内レッスンの合計金額を計算するメソッド
	public int calculateTotalPrice(LinkedList<Lesson> cart) {
		// カート内のすべてのレッスンの価格を合計
		if (cart != null) {
			return (int) cart.stream()
					.mapToDouble(Lesson::getLessonFee) // Lesson クラスに getPrice() メソッドがあると仮定
					.sum();
		} else {
			return 0;
		}
	}

	// カートに削除
	@GetMapping("/cart/delete/{lessonId}")
	public String deleteLessonFromCart(@PathVariable Long lessonId, Model model) {
		// session からカート情報を取得
		LinkedList<Lesson> cart = (LinkedList<Lesson>) session.getAttribute("cart");

		// カートが存在し、かつ空でないか確認
		if (cart != null && !cart.isEmpty()) {
			// カート内の指定した lessonId のレッスンを削除
			cart.removeIf(lesson -> lesson.getLessonId().equals(lessonId)); // 条件に一致するレッスンを削除
		}

		// session からユーザー情報を取得
		Users user = (Users) session.getAttribute("loginUserInfo");

		int total = calculateTotalPrice(cart);
		model.addAttribute("total", total);
		model.addAttribute("lessonList", cart);
		model.addAttribute("userName", user.getUserName());

		// カートページを返し、更新されたカートを表示
		return "user_cart_list.html";
	}

}
