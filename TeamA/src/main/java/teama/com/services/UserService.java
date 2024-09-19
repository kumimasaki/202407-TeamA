package teama.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.UsersDao;
import teama.com.models.entity.Users;

@Service
public class UserService {
	@Autowired
	private UsersDao usersDao;
	
	//登録処理
	public boolean createUser(String userName, String userEmail, String userPassword) {
		if(usersDao.findByUserEmail(userEmail) == null) {
			usersDao.save(new Users(userName, userEmail, userPassword));
			return true;
		} else {
			return false;
		}
	}
	
	//ログイン処理
	public Users loginCheck(String userEmail, String userPassword) {
		Users user = usersDao.findByUserEmailAndUserPassword(userEmail, userPassword);
		if(user == null) {
			return null;
		}else {
			return user;
		}
	}
	
	 public void saveUser(Users user) {
	        usersDao.save(user);
	    }
}
