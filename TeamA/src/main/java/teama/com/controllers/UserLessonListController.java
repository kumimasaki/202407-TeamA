package teama.com.controllers;

import java.util.ArrayList;
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

    // 1. 课程列表页面显示
    @GetMapping("/lesson/list")
    public String getLessonList(Model model) {
        // 通过session检查用户登录信息
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";
        }

        // 从服务层获取所有课程信息
        List<Lesson> lessonList = lessonServiceShao.selectAllLessonList(null);
        model.addAttribute("lessonList", lessonList); // 传递课程列表到前端
        return "lesson_list";  // 返回课程列表页面的HTML
    }

    // 2. 搜索课程功能
    @GetMapping("/search")
    public String searchLesson(@RequestParam("query") String query, Model model) {
        if (query == null || query.isEmpty()) {
            return "redirect:/lesson/list";  // 如果查询为空，返回课程列表页面
        }

        // 使用课程名查询
        Lesson lesson = lessonServiceShao.searchLessonByName(query);
        if (lesson != null) {
            model.addAttribute("lessonList", List.of(lesson));  // 如果找到课程，返回搜索结果
        } else {
            model.addAttribute("message", "该课程未找到。");
        }

        return "lesson_list";  // 返回课程列表页面
    }

    // 3. 将课程添加到购物车
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("lessonId") Long lessonId, Model model) {
        Users user = (Users) session.getAttribute("loginUserInfo");
        if (user == null) {
            return "redirect:/user/login";  // 如果用户没有登录，跳转到登录页面
        }

        // 假设购物车对象存储在session中
        List<Long> cart = (List<Long>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 检查课程是否存在并添加到购物车
        Lesson lesson = lessonServiceShao.lessonEditCheck(lessonId);
        if (lesson != null) {
            cart.add(lessonId);  // 购物车存储课程ID
            session.setAttribute("cart", cart);  // 更新session中的购物车
        } else {
            model.addAttribute("message", "该课程未找到。");
        }

        return "redirect:/lesson/list";  // 添加成功后重定向回课程列表
    }

    // 4. 导航按钮的处理（如跳转到“购买历史”或“信息更改”等页面）
    @GetMapping("/navigate")
    public String navigateTo(@RequestParam("page") String page) {
        switch (page) {
            case "history":
                return "redirect:/purchase/history";  // 购买历史页面
            case "profile":
                return "redirect:/user/profile";  // 信息更改页面
            case "logout":
                session.invalidate();  // 注销用户
                return "redirect:/user/login";  // 跳转到登录页面
            default:
                return "redirect:/lesson/list";  // 默认返回课程列表
        }
    }
}
