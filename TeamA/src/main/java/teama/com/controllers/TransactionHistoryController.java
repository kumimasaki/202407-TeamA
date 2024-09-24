package teama.com.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import teama.com.models.entity.Users;
import teama.com.services.TransactionHistoryService;
import teama.com.services.UserService;

@Controller
public class TransactionHistoryController {

    @Autowired
    private HttpSession session;

    @Autowired
//    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private UserService userService;

    // 显示购买历史
    @GetMapping("/transaction/history")
    public String showTransactionHistory(Model model) {
        // 从 session 获取当前登录的用户信息
        Users user = (Users) session.getAttribute("user");

        if (user != null) {
            // 获取该用户的购买历史
//            List<TransactionHistory> transactionHistoryList = transactionHistoryService.getTransactionHistoryByUser(user.getUserId());

            // 将购买历史传递到视图
//            model.addAttribute("transactionHistoryList", transactionHistoryList);
            model.addAttribute("userName", user.getUserName());

            // 返回购买历史页面
            return "user_transaction_history.html";
        } else {
            // 如果用户未登录，重定向到登录页面
            return "redirect:/user/login";
        }
    }
}
