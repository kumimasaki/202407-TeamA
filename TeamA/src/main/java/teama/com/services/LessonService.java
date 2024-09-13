package teama.com.services;

import java.time.LocalDate;
import java.util.Date;
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
public boolean createLesson(
		Date startDate,
		DateTime startTime,
		DateTime finishTime,
		String lessonName,
		String lessonDetail,
		int lessonFee,
		String imageName,
		Long adminId
		) {
	if (lessonDao.findByLessonName(lessonName) == null) {
		lessonDao.save(new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName, adminId));
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
if(lessonId == null) {
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
	if(imageName == null) {
		return null;
	} else {
//		return lessonDao.findByImageName(imageName);
		return null;
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
	if(imageName == null) {
		return false;
	} else {
		Lesson img = lessonDao.findByImageName(imageName);
		img.setImageName(imageName);
		lessonDao.save(img);
		return true;
	}
}



}
