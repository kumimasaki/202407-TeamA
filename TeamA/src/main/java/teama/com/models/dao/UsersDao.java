package teama.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teama.com.models.entity.Users;

@Repository
public interface UsersDao extends JpaRepository<Users, Long> {
	
	//　保存処理と更新処理insert, update
	Users save(Users users);

	// SELECT * FROM users WHERE user_email = ?
	//ユーザーの登録処理する時に、同じメールアドレスを登録させない
	Users findByUserEmail(String userEmail);

	//　SELECT * FROM users WHERE user_email and password =?
	Users findByUserEmailAndUserPassword(String userEmail, String userPassword);

}
