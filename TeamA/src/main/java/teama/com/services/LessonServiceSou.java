package teama.com.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.LessonDao;
import teama.com.models.entity.Lesson;

@Service
public class LessonServiceSou {
	@Autowired
	private LessonDao lessonDao;

	// 講座一覧のチェック
	// もしadminId==null 戻り値としてnull
	// findAll内容をコントローラークラスに渡す
	public List<Lesson> selectAllLessonList(Long adminId) {
		if (adminId == null) {
			return null;
		} else {
			return lessonDao.findAll();
		}
	}

	// 講座の登録処理チェック
	// もし、findByLessonNameが==nullだったら、
	// 保存処理
	// true
	// そうでない場合
	// false
	// entityのlessonを見て順番に書く
	public boolean createLesson(LocalDate startDate, LocalDateTime startTime, LocalDateTime finishTime,
			String lessonName, String lessonDetail, int lessonFee, String imageName, Long adminId) {
		if (lessonDao.findByLessonName(lessonName) == null) {
			lessonDao.save(new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName,
					adminId));
			return true;
		} else {
			return false;

		}
	}

	// 編集画面を表示するときのチェック
	// もし、lessonId == null null
	// そうでない場合、
	// findByAdminIdの情報をコントローラークラスに渡す
	public Lesson lessonEditCheck(Long lessonId) {
		if (lessonId == null) {
			return null;
		} else {
			return lessonDao.findByLessonId(lessonId);
		}
	}
	// 更新処理のチェック
	// もし、lessonId==nullだったら、更新処理はしない
	// false
	// そうでない場合、
	// 更新処理をする
	// コントローラークラスからもらった、lessonIdを使って、編集する前の、データを取得
	// 変更するべきところだけ、セッターを使用してデータの更新をする。
	// trueを返す
	//
	public boolean lessonUpdate(Long lessonId,
			LocalDate startDate,
			LocalDateTime startTime,
			LocalDateTime finishTime,
			String lessonName,
			String lessonDetail,
			int lessonFee,
			String imageName,
			Long adminId) {
		if(lessonId == null) {
			return false;
		}else {
			Lesson lesson = lessonDao.findByLessonId(lessonId);
			lesson.setStartDate(startDate);
			lesson.setStartTime(startTime);
			lesson.setFinishTime(finishTime);
			lesson.setImageName(imageName);
			lesson.setLessonDetail(lessonDetail);
			lesson.setLessonFee(lessonFee);
			lesson.setImageName(imageName);
			lesson.setAdminId(adminId);
			lessonDao.save(lesson);
			return true;
		}
	}

}
