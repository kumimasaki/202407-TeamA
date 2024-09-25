package teama.com.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import teama.com.services.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminReigisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService adminService;
	
	// サービスクラスを使ったデータ作成
		@BeforeEach
		public void prepareData() {
			// 登録できる場合　"Akemi", "cat@test.com", "cat1234" "cat1234"  true
			when(adminService.createAdmin("Akemi", "cat@test.com", "cat1234", "cat1234")).thenReturn(true);
//			// 登録できない場合　名前は"oukeni"、メールアドレスは"cat@test.com"と等しいこと　パスワードはどんな値でもいい　false
			when(adminService.createAdmin(eq("oukeni"), any(), any(), any())).thenReturn(false);
//			when(adminService.createAdmin(eq("Akemi"),eq("dog"), eq("cat1234"), eq("cat1234"))).thenReturn(false);
//			when(adminService.createAdmin(eq("Akemi"),eq("cat@test.com"), eq("cat1234"), eq("dog"))).thenReturn(false);

		}
		
		// 登録画面が正常表示できるテスト
		@Test
		public void testGetRegisterPage_Succeed() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders
					.get("/admin/register");
			
			mockMvc.perform(request)
			.andExpect(view().name("admin_register.html"));
		}

//		adminPassword.notEquals(confirmAdminPassword）テスト
		@Test
		public void testAdminPassword_eqConfirmAdminPassword_isNotEq() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders
					.post("/admin/register/process")
					.param("adminName", "Akemi")
					.param("adminEmail", "cat@test.com")
					.param("adminPassword", "cat1234")
					.param("confirmAdminPassword", "dog");
			mockMvc.perform(request)
			.andExpect(view().name("admin_register.html"));
		}
		
		
		// /adminの登録が成功するかのテスト
		@Test
		public void testRegister_NewAdmin_Succeed() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders
					.post("/admin/register/process")
					.param("adminName", "Akemi")
					.param("adminEmail", "cat@test.com")
					.param("adminPassword", "cat1234")
					.param("confirmAdminPassword", "cat1234");
			mockMvc.perform(request)
			.andExpect(view().name("admin_login.html"));
		}
		
		// admin登録が失敗するかのテスト
		@Test
		public void testRegister_ExistingAdmimName_Fail() throws Exception {
			RequestBuilder request = MockMvcRequestBuilders
					.post("/admin/register/process")
					.param("adminName", "oukeni")
					.param("adminEmail", "cat@test.com")
					.param("adminPassword", "cat1234")
					.param("confirmAdminPassword", "cat1234");
			mockMvc.perform(request)
//			.andExpect(model().attribute("error", true))
			.andExpect(view().name("admin_register.html"));
		}
}
