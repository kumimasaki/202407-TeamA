package teama.com.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teama.com.models.dao.LessonDaoShao;
import teama.com.models.entity.Lesson;

@Service
public class LessonServiceShao {

    @Autowired
    private LessonDaoShao lessonDaoShao;

    public List<Lesson> selectAllLessonList(Long adminId) {
        if (adminId == null) {
            return null;
        } else {
            return lessonDaoShao.findAll();
        }
    }

    // lessonの登録処理チェック
    public boolean createLesson(
            LocalDate startDate,
            LocalDateTime startTime,
            LocalDateTime finishTime,
            String lessonName,
            String lessonDetail,
            int lessonFee,
            String imageName,
            Long adminId
    ) {
        if (lessonDaoShao.findByLessonName(lessonName) == null) {
            Lesson lesson = new Lesson(startDate, startTime, finishTime, lessonName, lessonDetail, lessonFee, imageName, adminId);
            lessonDaoShao.save(lesson);
            return true;
        } else {
            return false;
        }
    }

    // lesson編集画面を表示する時のチェック
    public Lesson lessonEditCheck(Long lessonId) {
        if (lessonId == null) {
            return null;
        } else {
            return lessonDaoShao.findByLessonId(lessonId);
        }
    }

    // image編集画面を表示する時のチェック
    public Lesson imageEditCheck(String imageName) {
        if (imageName == null) {
            return null;
        } else {
            return lessonDaoShao.findByImageName(imageName);
        }
    }

    // image更新処理のチェック
    public boolean imageUpdate(String imageName) {
        if (imageName == null) {
            return false;
        } else {
            Lesson img = lessonDaoShao.findByImageName(imageName);
            if (img != null) {
                img.setImageName(imageName);
                lessonDaoShao.save(img);
                return true;
            } else {
                return false;
            }
        }
    }
    public Lesson searchLessonByName(String lessonName) {
        return lessonDaoShao.findByLessonName(lessonName);
    }
}
