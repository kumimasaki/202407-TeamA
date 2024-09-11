package ec.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.models.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {
	Admin save(Admin admin);

	Admin findByAdminEmail(String adminEmail);

	Admin findByAdminEmailAndAdminPassword(String adminEmail, String password);

}
