package teama.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teama.com.models.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Long> {
	Users save(Users users);

	Users findByUserEmail(String userEmail);

	Users findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
