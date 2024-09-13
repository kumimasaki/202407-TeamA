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
	public boolean createLesson(LocalDate startDate, 
			LocalDateTime startTime, 
			LocalDateTime finishTime,
			String lessonName, 
			String lessonDetail, 
			int lessonFee, 
			String imageName, 
			Long adminId) {
		if (lessonDao.findByLessonName(lessonName) == null) {
			lessonDao.save(new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName,
					adminId));
			return true;
		} else {
			return false;

		}
	}
}
