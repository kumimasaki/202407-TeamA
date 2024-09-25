package teama.com.controllers;

import java.util.LinkedList;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Lesson;
import teama.com.models.entity.Users;
import teama.com.services.LessonService;

@Controller
public class TransactionController {
	@Autowired
	private LessonService lessenService;
	
	//Sessionを使えるように宣言
	@Autowired
	private HttpSession session;
	
	//支払い選択画面表示
	@GetMapping("/users/selectPayment")
	public String getSelectPaymentPage(Model model) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

		LinkedList<Lesson> list = (LinkedList<Lesson>) session.getAttribute("cart");
		model.addAttribute("lessonList", list);
		model.addAttribute("userName", user.getUserName());
		return "users_select_payment.html";
	}
	
    // 支払い方法選択後、購入確認画面に進む
	@PostMapping("/users/selectPayment/process")
    public String processPaymentSelection(@RequestParam String circleOption, HttpSession session) {
        // 支払い方法をセッションに保存
        session.setAttribute("paymentMethod", circleOption);
        return "redirect:/users/lesson/confirm"; // 確認画面にリダイレクト
    }
	
	// 申し込み内容確認画面表示
	@GetMapping("/users/lesson/confirm")
	public String getConfirmContent(Model model ) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        String paymentMethod = (String) session.getAttribute("paymentMethod");
        if (user == null) {
            return "redirect:/user/login";
        }

        //セッションからカートの内容を取得
        LinkedList<Lesson> list = (LinkedList<Lesson>) session.getAttribute("cart");
		model.addAttribute("lessonList", list);
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("paymentMethod", paymentMethod);
		return "users_confirm_content.html";
	}
	
	
	// 購入確認後、購入完了画面に進む
	@GetMapping("/users/procedureCompleted")
	public String getProcedureCompletedPage(Model model) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

        // カートと支払い方法をセッションから削除
        session.removeAttribute("cart");
        session.removeAttribute("paymentMethod");
        
        // セッションから取得したデータを履歴テーブルに登録
        
		return "users_procedure_completed.html";
	}
	
	//申し込み内容確認画面から戻る
	@PostMapping("/users/confirm/reset")
	public String resetconfirmContent() {
		return "users_procedure_completed.html";
	}
	
	//メニューから他の画面へ移動
    @GetMapping("/navigate")
    public String navigateTo(@RequestParam("page") String page) {
        switch (page) {
            case "history":
                return "redirect:/purchase/history";  // 購入履歴ページ
            case "profile":
                return "redirect:/user/profile";  // プロフィール変更ページ
            case "logout":
                session.invalidate();  // ユーザーをログアウト
                return "redirect:/user/login";  // ログインページにリダイレクト
            default:
                return "redirect:/lesson/list";  // デフォルトでコース一覧に戻る
        }
    }

}
