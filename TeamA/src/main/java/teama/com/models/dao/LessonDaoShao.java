package teama.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teama.com.models.entity.Lesson;

@Repository
public interface LessonDaoShao extends JpaRepository<Lesson, Long> {
	
   
    Lesson findByLessonName(String lessonName);
	
    List<Lesson> findAll();
   
    Lesson findByLessonId(Long lessonId);
	
    Lesson findByImageName(String imageName);
	
    void deleteByLessonId(Long lessonId);
}
