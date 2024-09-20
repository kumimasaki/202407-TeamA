package teama.com.controllers;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teama.com.models.entity.Lesson;
import teama.com.models.entity.Users;
import teama.com.services.LessonService_Zou;

@Controller 
public class UserLessonListControllerZou {

    @Autowired
    private LessonService_Zou lessonServiceZou;

    @Autowired
    private HttpSession session;

    // 1. コース一覧ページの表示
    @GetMapping("/user/lesson/list/test")
    public String getLessonList(Model model) {
        // セッションを通じてユーザーログイン情報を確認
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

        // サービス層からすべてのコース情報を取得
        List<Lesson> lessonList = lessonServiceZou.selectAllLessonList(null);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("lessonList", lessonList); // コース一覧をフロントに渡す
        
        return "user/lesson_list";  // コース一覧ページのHTMLを返す
    }

    // 2. コース検索機能
    @GetMapping("/search")
    public String searchLesson(@RequestParam("query") String query, Model model) {
        if (query == null || query.isEmpty()) {
            return "redirect:/user/lesson/list";  // 検索クエリが空の場合、コース一覧ページに戻る
        }

        // コース名で検索
		Lesson lesson = lessonServiceZou.searchLessonByName(query);
        if (lesson != null) {
            model.addAttribute("lessonList", List.of(lesson));  // コースが見つかれば、検索結果を表示
        } else {
            model.addAttribute("message", "そのコースは見つかりませんでした。");
        }

        return "lesson_list";  // コース一覧ページを返す
    }

//    // 3. コースをカートに追加する機能
//    @PostMapping("/cart/add")
//    public String addToCart(@RequestParam("lessonId") Long lessonId, Model model) {
//        Users user = (Users) session.getAttribute("loginUserInfo");
//        if (user == null) {
//            return "redirect:/user/login";  // ユーザーがログインしていない場合、ログインページにリダイレクト
//        }
//
//        // 仮にカートオブジェクトがセッション内に保存されているとする
//        List<Long> cart = (List<Long>) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new ArrayList<>();
//        }
//
//        // コースが存在するか確認してカートに追加
//        Lesson lesson = lessonServiceZou.lessonEditCheck(lessonId);
//        if (lesson != null) {
//            cart.add(lessonId);  // カートにコースIDを保存
//            session.setAttribute("cart", cart);  // セッション内のカートを更新
//        } else {
//            model.addAttribute("message", "そのコースは見つかりませんでした。");
//        }
//
//        return "redirect:/lesson/list";  // 追加後にコース一覧にリダイレクト
//    }

    // 4. ナビゲーションボタンの処理（購入履歴やプロフィール変更ページへの移動）
    @GetMapping("/navigate/test")
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