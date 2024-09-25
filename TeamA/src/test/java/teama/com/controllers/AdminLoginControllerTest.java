package teama.com.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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
import teama.com.models.entity.Admin;
import teama.com.services.AdminService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// サービス層を呼び出し
	@MockBean
	private AdminService accountService;

	@BeforeEach
	public void prepareData() {
		Admin alice = new Admin("Akemi", "cat@test.com", "cat1234", "cat1234");

		when(accountService.checkLogin("cat@test.com", "cat1234")).thenReturn(alice);

		when(accountService.checkLogin("test@test.com", "1234")).thenReturn(null);
	}

	// ログインページを正しく取得するテスト
	@Test
	public void testGetLoginPage_Succeed() throws Exception {
//		   クライアントから送信される HTTP リクエストを模擬する には、MockMvcRequestBuilders が提供するメソッドを 使用して、リクエストを手動で生成する必要があります
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/login");
		mockMvc.perform(request).andExpect(view().name("admin_login.html"));
	}

	// ログインが成功した場合のテスト
		// セッションからログインユーザーエンティティを取得し、それがnullでないことを検証
		@Test
		public void testLoginCheck_Account_Succeed() throws Exception {
			// テスト用のMockHttpSessionを作成
			MockHttpSession session = new MockHttpSession();
			// セッションの設定
			Admin admin = new Admin();
			admin.setAdminEmail("cat@test.com");
			admin.setAdminName("Alice");
			admin.setAdminPassword("cat1234");
			session.setAttribute("admin", admin);

			// POSTリクエストを作成
			RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process")
					                                       .session(session)
					                                       .param("adminEmail", "cat@test.com")
					                                       .param("adminPassword", "cat1234");

				// リクエストを実行してレスポンスを取得
				MvcResult result = mockMvc.perform(request)
						                   .andExpect(view().name("redirect:/lesson/list"))
				                           .andReturn();

			// セッションから "account" を取得して null ではないことを確認
			HttpSession sessionAfterRequest = result.getRequest().getSession();
			Object accountInSession = sessionAfterRequest.getAttribute("admin");
		    assertNotNull(accountInSession, "sessionはNULLではない");
		}
	//"/login" へのGETリクエストを作成し、セッションからログインユーザーエンティティを取得し、それがnullであることを検証
	//ログインユーザーエンティティのユーザー名が "Akemi" であり、メールアドレスが "ake@test.com"であることを検証    
		@Test
		public void testLoginCheckIsNullInSession() throws Exception {
			// GETリクエストを送信してセッションを取得
			HttpSession session = mockMvc.perform(get("/admin/login")).andExpect(status().isOk()).andReturn().getRequest()
					.getSession();

			// セッションからUserEntityを取得してnullであることを検証
			Object userInSession = session.getAttribute("admin");
			assertNull(userInSession, "sessionはNULLである");
		}

		
		
		
		
		
		// ログインが失敗した場合のテスト,両方間違える
		@Test
		public void testLoginCheck_InValidAccountEmailAndPassword_Fail() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process").param("adminEmail", "test@test.com")
					.param("adminPassword", "1234");
			mockMvc.perform(request).andExpect(view().name("admin_login.html"));
		}

		// ログインが失敗した場合のテスト,email間違える
		@Test
		public void testLoginCheck_InValidAccountEmail_Fail() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process").param("adminEmail", "test@test.com")
					.param("adminPassword", "cat1234");
			mockMvc.perform(request).andExpect(view().name("admin_login.html"));
		}

		// ログインが失敗した場合のテスト,password間違える
		@Test
		public void testLoginCheck_InValidPassword_Fail() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process").param("adminEmail", "cat@test.com")
					.param("adminPassword", "1234");
			mockMvc.perform(request).andExpect(view().name("admin_login.html"));
		}
	}