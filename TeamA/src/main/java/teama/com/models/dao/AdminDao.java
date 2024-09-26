package teama.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teama.com.models.entity.Admin;

//Serviceに対して、Entityのライフサイクルを制御するための操作（Repositoryインタフェース）を提供する。
@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {
	// 保存処理と更新処理 insertとupate
	Admin save(Admin admin);

	// 管理者の登録処理をする時に、同じメールアドレスがあったらば登録させないようにする
	// 1行だけしかレコードは取得できない
	Admin findByAdminEmail(String adminEmail);

	// ログイン処理に使用。入力したメールアドレスとパスワードが一致してる時だけデータを取得する
	Admin findByAdminEmailAndAdminPassword(String adminEmail, String password);

	// ログイン処理に使用。入力したメールアドレスとadminConfirmPasswordが一致してる時だけデータを取得する
	Admin findByAdminPasswordAndAdminConfirmPassword(String adminPassword, String adminConfirmPassword);

}
