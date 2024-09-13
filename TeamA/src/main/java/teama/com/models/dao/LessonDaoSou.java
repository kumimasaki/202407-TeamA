package teama.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import teama.com.models.entity.Lesson;

@Repository
@Transactional // 削除の時使う
public interface LessonDaoSou extends JpaRepository<Lesson, Long> {
	// 保存処理と更新処理 insertとupate
	Lesson save(Lesson lesson);

	// 講座一覧を表示
	// SELECT * FROM lesson (複数取得)
	List<Lesson> findAll();

	// 講座の登録チェックに使用（同じ講座名がブログされないようにチェックに使用）(単一取得)
	// SELECT * FROM lesson WHERE lesson-name = ?
	Lesson findByLessonName(String lessonName);

	// 編集画面を表示(単一取得)
	// SELECT * FROM lesson WHERE lesson_id = ?
	Lesson findByLessonId(Long lesson_id);

	// 削除使用します(単一取得)
	// DLETE FROM lesson WHERE lesson-id = ?
	void deleteByLessonId(Long lesson_id);
}
