package teama.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Lesson;
import teama.com.models.entity.Users;
import teama.com.services.LessonServiceShao;

@Controller
public class TransactionController {
	@Autowired
	private LessonServiceShao lessenService;
	
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

        // サービス層からすべてのコース情報を取得
        List<Lesson> lessonList = lessenService.selectAllLessonList(null);
        //セッションからカートの内容を取得
        session.getAttribute("cart"); 
		return "users_select_payment.html";
	}
	
	//申し込み内容確認画面表示
	@GetMapping("/users/confirm")
	public String getConfirmContent(Model model ) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

        //セッションからカートの内容を取得
        session.getAttribute("cart"); 
		return "users_confirm_content.html";
	}
	
	//購入完了
	@GetMapping("users/procedureCompleted")
	public String getProcedureCompletedPage(Model model) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

        //セッションからカートの内容を取得
        session.getAttribute("cart"); 
        //セッションから購入したカートの内容を削除
		return "users_procedure_completed.html";
	}
	
    // 支払い方法選択後、購入確認画面に進む
    @PostMapping("/users/selectPayment/process")
    public String processPaymentSelection(String paymentMethod, HttpSession session) {
        // 支払い方法をセッションに保存
        session.setAttribute("paymentMethod", paymentMethod);
        return "redirect:/users/confirm"; // 確認画面にリダイレクト
    }
	
    // 購入確認後、購入完了画面に進む
    @PostMapping("/users/confirm/process")
    public String processPurchaseConfirmation(HttpSession session) {
        // 購入処理を行う（例えば、データベースに保存したり、在庫管理など）
        
        // カートと支払い方法をセッションから削除
        session.removeAttribute("cart");
        session.removeAttribute("paymentMethod");

        return "redirect:/users/procedureCompleted"; // 購入完了画面にリダイレクト
    }
	
	//購入完了画面＞＞商品一覧画面
	@PostMapping("/users/procedureCompleted/process")
	public String procedureCompletedProcess() {
		return "user_cart_list.html";
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
