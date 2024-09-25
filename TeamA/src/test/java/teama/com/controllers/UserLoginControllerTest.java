package teama.com.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.servlet.http.HttpSession;
import teama.com.models.entity.Users;
import teama.com.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	// サービスクラスを使ったデータを作成
	@BeforeEach
	public void prepareData() {
		Users akemi = new Users("Akemi", "ake@test.com", "1234abcd");
		// ログイン成功：accountEmail "ake@test.com"、 password 1234abcd" true
		when(userService.loginCheck("ake@test.com", "1234abcd")).thenReturn(akemi);
//		// ログイン失敗:accountEmail "Nonexist@test.com", password "1234abcd" false
		when(userService.loginCheck("Nonexist@test.com", "1234abcd")).thenReturn(null);
//		// ログイン失敗:accountEmail "ake@test.com"、 password "4321dcba" false
		when(userService.loginCheck("ake@test.com", "4321dcba")).thenReturn(null);
		// ログイン失敗:accountEmail "Nonexist@test.com"、 password "4321dcba" false
		when(userService.loginCheck("Nonexist@test.com", "4321dcba")).thenReturn(null);
	}
	
	// ログインページを正しく取得するテスト
	@Test
	public void testUserLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/user/login");
		mockMvc.perform(request).andExpect(view().name("userLogin.html"));
	}
	
	// ログインが成功した場合
	//「user_lesson_list.html」に遷移して、入力された値が渡されているかのテスト
	@Test
	public void testUserEntityInSession()throws Exception {
		// テスト用MockHttpSession作成
		MockHttpSession session = new MockHttpSession();
		//セッション設定
		Users user = new Users();
		user.setUserId(1L);
		user.setUserName("Akemi");
		user.setUserEmail("ake@test.com");
		user.setUserPassword("1234abcd");
		session.setAttribute("user", user);
		
//	// POSTリクエストを作成
		RequestBuilder request = MockMvcRequestBuilders.post("/user/login/process")
				.session(session)
				.param("userEmail", "ake@test.com")
				.param("userPassword", "1234abcd");
		
		// リクエスト実行してレスポンス取得
		MvcResult result = mockMvc.perform(request).andExpect(view().name("redirect:/user/lesson/list")).andReturn();
		
		// セッションから"user"を取得しnullではないことを確認
		HttpSession sessionAfterRequest = result.getRequest().getSession();
		Object userInSession = sessionAfterRequest.getAttribute("user");
		assertNotNull(userInSession);
	}
	
	
	//ログインが失敗した場合
	//メールアドレスだけが間違った場合
	@Test
	public void testUserLoginProcess_IncorrectEmail_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/user/login/process")
				.param("userEmail", "Nonexist@test.com").param("userPassword", "1234abcd");

		mockMvc.perform(request).andExpect(view().name("userLogin.html"));
	}

	// ログインが失敗した場合
	// パスワードだけが間違った場合
	@Test
	public void testUserLoginProcess_IncorrectPassword_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/user/login/process")
				.param("userEmail", "ake@test.com").param("userPassword", "4321dcba");

		mockMvc.perform(request).andExpect(view().name("userLogin.html"));
	}

	// ログインが失敗した場合
	// メールアドレスとパスワードが全部間違った場合
	@Test
	public void testUserLoginProcess_IncorrectEmailAndPassword_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/user/login/process")
				.param("userEmail", "Nonexist@test.com").param("userPassword", "4321dcba");

		mockMvc.perform(request).andExpect(view().name("userLogin.html"));
	}

	@Test
	public void testUserEntityIsNullInSession() throws Exception {
		// GETリクエストを送信してセッションを取得
		HttpSession session = mockMvc.perform(get("/user/login")).andExpect(status().isOk()).andReturn().getRequest()
				.getSession();

		// セッションからUserEntityを取得してnullであることを検証
		Object userInSession = session.getAttribute("user");
		assertNull(userInSession, "sessionはNULLである");
	}
	
}
