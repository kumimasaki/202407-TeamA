package teama.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Users;
import teama.com.services.UserService;

@Controller
public class UserRegisterController {
    
    @Autowired
    private UserService userService;
    
    // 登録画面の表示
    @GetMapping("/user/register")
    public String getUserRegisterPage() {
        return "userRegister.html";
    }
    
    // 登録処理
    @PostMapping("/user/register/process")
    public String userRegisterProcess(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String userPassword, Model model) {
        // ユーザーの作成を試みる
        if (userService.createUser(userName, userEmail, userPassword)) {
            // 登録成功、ユーザー情報をモデルに保存
            Users user = new Users(userName, userEmail, userPassword);
            model.addAttribute("user", user); // 将用户信息添加到模型中
            return "user_confirm.html";   // 登録成功、確認ページを表示
        } else {
            // 登録失敗、登録ページに戻りエラーメッセージを表示
            model.addAttribute("error", "登録に失敗しました。もう一度お試しください。"); // エラーメッセージを追加
            return "userRegister.html";  // 登録失敗、登録ページに戻る
        }
    }
    
    // 登録確認画面の表示
    @GetMapping("/user/confirm")
    public String userConfirm(@ModelAttribute Users user, Model model) {
        // モデルにユーザー情報を追加
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("userEmail", user.getUserEmail());
        model.addAttribute("userPassword", user.getUserPassword());
        return "user_confirm.html"; // 確認画面を表示
    }
    
    // 登録確認処理
    @PostMapping("/user/confirm/check")
    public String userConfirmProcess() {
        // 確認が完了したらログイン画面にリダイレクト
        return "redirect:/user/login"; // 重定向到登录页面
    }
    
    // 登録内容をリセットして、登録画面に戻るメソッド
    @PostMapping("/user/confirm/reset")
    public String resetRegistration(Model model) {
        model.addAttribute("user", new Users()); // 空のユーザーオブジェクトを渡す
        return "userRegister.html"; // 再度登録画面に戻る
    }
}

