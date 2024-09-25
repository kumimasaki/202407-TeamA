package teama.com.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.LessonDao;
import teama.com.models.entity.Lesson;

//Serviceクラス、機能の概要、クラスを自動生成することもあります
@Service
public class LessonService {

	// @Autowiredアノテーションをつけて、自動的にインターフェースを実装して、インスタンス化させて、ControllerでHttpSessionを使えるようにします
	@Autowired
	private LessonDao lessonDao;

	// 講座一覧のチェック
	public List<Lesson> selectAllLessonList(Long adminId) {
		// もしadminId==null 戻り値としてnull
		// findAll内容をコントローラークラスに渡す
		if (adminId == null) {
			return null;
		} else {
			return lessonDao.findAll();
		}
	}

	public List<Lesson> selectAllLessonListShao() {
		return lessonDao.findAll();
	}

	// 講座の登録処理チェック
	public boolean createLesson(LocalDate startDate, LocalTime startTime, LocalTime finishTime, String lessonName,
			String lessonDetail, int lessonFee, String imageName, Long adminId) {
		// もし、findByLessonName==null
		if (lessonDao.findByLessonName(lessonName) == null) {
			lessonDao.save(new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName,
					adminId));
			// 保存処理 true
			return true;
			// そうでない場合
		} else {
			// false
			return false;
		}
	}

	// lesson編集画面を表示する時のチェック
	public Lesson lessonEditCheck(Long lessonId) {
		// もし、lessonId == null
		if (lessonId == null) {
			// null
			return null;
			// そうでない場合、
		} else {
			// findBylessonIdの情報をコントローラークラスに渡す
			return lessonDao.findByLessonId(lessonId);
		}
	}

	// image編集画面を表示する時のチェック
	public Lesson imageEditCheck(String imageName) {
		// もし、imageName == null
		if (imageName == null) {
			// null
			return null;
			// そうでない場合
		} else {
			// findByImageNameの情報をコントローラークラスに渡す
			return lessonDao.findByImageName(imageName);
		}
	}

	// image更新処理のチェックの
	public boolean imageUpdate(String imageName) {
		// もし、imageName=nullだったら、更新処理はしない
		if (imageName == null) {
			// false
			return false;
			// そうでない場合、更新処理をする
		} else {
			// コントローラークラスからもらった、imageNameを使って、編集する前のデータを取得
			Lesson img = lessonDao.findByImageName(imageName);
			// 変更するべきところだけ、セッターを使用してデータの更新をする
			img.setImageName(imageName);
			lessonDao.save(img);
			// trueを返す
			return true;
		}
	}

	// lesson更新処理のチェックの
	public boolean lessonUpdate(Long lessonId, LocalDate startDate, LocalTime startTime, LocalTime finishTime,
			String lessonName, String lessonDetail, int lessonFee, String imageName, Long adminId) {
		// もし、lessonId=nullだったら、更新処理はしない
		if (lessonId == null) {
			// false
			return false;
			// そうでない場合、更新処理をする
		} else {
			// コントローラークラスからもらった、lessonIdを使って、編集する前のデータを取得
			Lesson lesson = lessonDao.findByLessonId(lessonId);
			// 変更するべきところだけ、セッターを使用してデータの更新をする
			lesson.setStartDate(startDate);
			lesson.setStartTime(startTime);
			lesson.setFinishTime(finishTime);
			lesson.setLessonName(lessonName);
			lesson.setLessonDetail(lessonDetail);
			lesson.setLessonFee(lessonFee);
			lesson.setImageName(imageName);
			lesson.setAdminId(adminId);
			lessonDao.save(lesson);
			// trueを返す
			return true;
		}
	}

	// 削除処理のチェック
	public boolean deleteByLesson(Long lessonId) {
		// もし、コントローラークラスから受け取ったlessonIdがnull
		if (lessonId == null) {
			// 削除できないことfalse
			return false;
			// 削除できる場合
		} else {
			// deleteByLessonIdを使って削除処理
			lessonDao.deleteByLessonId(lessonId);
			// true
			return true;
		}
	}

	// 保存
	public void saveLesson(Lesson lesson) {
		lessonDao.save(lesson);
	}

	// 講座を検索する処理 Shao
	public Lesson findByLessonId(Long lessonId) {
		return lessonDao.findByLessonId(lessonId);
	}

}
