//package teama.com.controllers;
//
//import java.util.List;
//
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import teama.com.services.LessonService;
//
//@Controller
//public class UserLessonListController {
//
//	@Autowired
//	private HttpSession session;
//
//	@Autowired
//	private LessonService lessonService;
//	
//	// 一覧画面を表示する
//		@GetMapping("/user/cartList")
//		public String getBlogList(Model model) {
//			// セッションからログインしているユーザーの情報を取得
//			Account account = (Account) session.getAttribute("loginAccountInfo");
//
//			// ログインしていない場合、ログインページへリダイレクト
//			if (account == null) {
//				return "redirect:/account/login";
//			} else {
//				// ユーザーのブログ情報を取得
//				List<Lessonlist> lessonList = lessonService.selectAllBlogList(account.getAccountId());
//				// モデルにユーザー名とブログリストを追加
//				model.addAttribute("accountName", account.getAccountName());
//				model.addAttribute("blogList", blogList);
//				// ブログ一覧ページを表示
//				return "user_lesson_list.html";
//			}
//		}
//}
