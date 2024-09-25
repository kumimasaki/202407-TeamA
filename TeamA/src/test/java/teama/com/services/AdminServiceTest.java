package teama.com.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.eq;
import teama.com.models.dao.AdminDao;
import teama.com.models.entity.Admin;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AdminServiceTest {
@MockBean
private AdminDao adminDao;

@Autowired
private AdminService adminService;

@BeforeEach
public void prepareData() {
	Admin akemi = new Admin("Akemi", "cat@test.com", "cat1234", "cat1234");
	when(adminDao.findByAdminEmailAndAdminPassword("cat@test.com", "cat1234")).thenReturn(akemi);
	when(adminDao.findByAdminEmailAndAdminPassword("test@test.com", "1234")).thenReturn(null);
    when(adminDao.findByAdminEmail("cat@test.com")).thenReturn(akemi);
	when(adminDao.findByAdminEmail(eq("test@test.com"))).thenReturn(null);
//	

}
//成功テスト
@Test
public void testValidateAccount_CorrectInfo_True() {
	assertNotNull(adminService.checkLogin("cat@test.com", "cat1234"));
}
//間違える
@Test
public void testValidateAccount_False() {
	assertNull(adminService.checkLogin("test@test.com", "1234"));
}
//登録テスト
@Test
public void checkAdmin_CorrectInfo_True() {
	assertTrue(adminService.createAdmin("Alice", "test@test.com", "1234", "1234"));
}
@Test
public void checkAdmin_CorrectInfo_False() {
	assertFalse(adminService.createAdmin("Akemi", "cat@test.com", "cat1234", "cat1234"));
}

}
