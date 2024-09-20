package teama.com.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.LessonDao;
import teama.com.models.entity.Lesson;

@Service
public class LessonService {
	@Autowired
	private LessonDao lessonDao;

	public List<Lesson> selectAllLessonList(Long adminId) {
		if (adminId == null) {
			return null;
		} else {
			return lessonDao.findAll();
		}
	}

	//	lessonの登録処理チェック
	//	もし、findByLessonName==null
	//			処理
	//			true
	//			そうでない場合
	//			false
	public boolean createLesson(LocalDate startDate, LocalTime startTime, LocalTime finishTime,
			String lessonName, String lessonDetail, int lessonFee, String imageName, Long adminId) {
		if (lessonDao.findByLessonName(lessonName) == null) {
			lessonDao.save(new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName,
					adminId));
			return true;
		} else {
			return false;
		}
	}

	//lesson編集画面を表示する時のチェック
	//もし、lessonId == null ，null
	//そうでない場合、
	//findBylessonIdの情報をコントローラーclassに渡す
	public Lesson lessonEditCheck(Long lessonId) {
		if (lessonId == null) {
			return null;
		} else {
			return lessonDao.findByLessonId(lessonId);
		}
	}

	//	image編集画面を表示する時のチェック
	//	もし、imageName == null ，null
	//	そうでない場合、
	//	findByImageNameの情報をコントローラーclassに渡す
	public Lesson imageEditCheck(String imageName) {
		if (imageName == null) {
			return null;
		} else {
			return lessonDao.findByImageName(imageName);
//		return null;
		}
	}

	//image更新処理のチェックの

	//もし、imageName=nullだったら、更新処理はしない
	//false
	//そうでない場合
	//更新処理をする
	//コントローラーclassからもらった、imageNameを使って、編集する前のデータを取得
	//変更するべきところだけ、セッターを使用してデータの更新をする
	//trueを返す
	public boolean imageUpdate(String imageName) {
		if (imageName == null) {
			return false;
		} else {
			Lesson img = lessonDao.findByImageName(imageName);
			img.setImageName(imageName);
			lessonDao.save(img);
			return true;
		}
	}

	//lesson更新処理のチェックの

	//もし、lessonId=nullだったら、更新処理はしない
	//false
	//そうでない場合
	//更新処理をする
	//コントローラーclassからもらった、lessonIdを使って、編集する前のデータを取得
	//変更するべきところだけ、セッターを使用してデータの更新をする
    //trueを返す
	public boolean lessonUpdate(Long lessonId, LocalDate startDate,
//		@Column(columnDefinition = "TIME")
			LocalTime startTime,
//		@Column(columnDefinition = "TIME")
			LocalTime finishTime, String lessonName, String lessonDetail, int lessonFee, String imageName,
			Long adminId) {
		if (lessonId == null) {
			return false;
		} else {
			Lesson lesson = lessonDao.findByLessonId(lessonId);
			lesson.setStartDate(startDate);
			lesson.setStartTime(startTime);
			lesson.setFinishTime(finishTime);
			lesson.setLessonName(lessonName);
			lesson.setLessonDetail(lessonDetail);
			lesson.setLessonFee(lessonFee);
			lesson.setImageName(imageName);
			lesson.setAdminId(adminId);
			lessonDao.save(lesson);
			return true;
		}
	}

	// 削除処理のチェック
	// もし、コントローラークラスから受け取ったlessonIdがnull
	// 削除できないことfalse
	// 削除できる場合、deleteByLessonIdを使って削除処理
	// true lesson_delete_complete.htmlを遷移する
	public boolean deleteByLesson(Long lessonId) {
		if (lessonId == null) {
			return false;
		} else {
			lessonDao.deleteByLessonId(lessonId);
			return true;
		}
	}
	//保存 Lesson 对象的方法
	public void saveLesson(Lesson lesson) {
	    lessonDao.save(lesson);  // 使用 JPA 保存到数据库
	}
}
