package teama.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teama.com.models.entity.Admin_togyoho;

@Repository
public interface AdminDao_togyoho extends JpaRepository<Admin_togyoho, Long> {

	Admin_togyoho save(Admin_togyoho admin_togyoho);
	
	Admin_togyoho findByAdminEmail(String adminEmail);
	
	Admin_togyoho findByAdminEmailAndAdminPassword(String adminEmail,String adminPassword);
	
	Admin_togyoho findByAdminPasswordAndAdminConfirmPassword(String adminPassword,String adminConfirmPassword);
	
}
