package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
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
		return "users_select_payment.html";
	}
	
	//申し込み内容確認画面表示
	@GetMapping("/users/confirm")
	public String getConfirmContent(Model model) {
		return "users_confirm_content.html";
	}
	
	//購入完了
	@GetMapping("users/procedureCompleted")
	public String getProcedureCompletedPage(Model model) {
		session.removeAttribute("cart");
		return "users_procedure_completed.html";
	}
	
	//支払い選択画面＞＞申し込み内容確認画面
	@PostMapping("/users/selectPayment/process")
	public String selectPaymentProcess() {
		return "users_confirm_content.html";
	}
	
	//申し込み内容確認画面＞＞購入完了画面
	@PostMapping("/users/confirm/process")
	public String cofirmContentProcess() {
		return "users_procedure_completed.html";
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

}
