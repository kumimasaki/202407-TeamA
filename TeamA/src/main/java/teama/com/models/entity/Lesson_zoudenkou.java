package teama.com.models.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Lesson_zoudenkou {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	//lessonIdの設定
	private Long lessonId;
	//startDateの設定
	private LocalDate startDate;
	//startTimeの設定
	private LocalDateTime startTime;
	//finishTimeの設定
	private LocalDateTime finishTime;
	//lessonNameの設定
	private String lessonName;
	//lessonDetailの設定
	private String lessonDetail;
	//lessonFeeの設定
	private int lessonFee;
	//imageNameの設定
	private String imageName;
	//adminIdの設定
	private Long adminId;
	
	//空のコンストラクタ
	public Lesson_zoudenkou() {
	}

	//コンストラクタ
	public Lesson_zoudenkou(LocalDate startDate, LocalDateTime startTime, LocalDateTime finishTime, String lessonName,
			String lessonDetail, int lessonFee, String imageName, Long adminId) {
		this.startDate = startDate;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.lessonName = lessonName;
		this.lessonDetail = lessonDetail;
		this.lessonFee = lessonFee;
		this.imageName = imageName;
		this.adminId = adminId;
	}
	
	//ゲッターとセッター
	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getLessonDetail() {
		return lessonDetail;
	}

	public void setLessonDetail(String lessonDetail) {
		this.lessonDetail = lessonDetail;
	}

	public int getLessonFee() {
		return lessonFee;
	}

	public void setLessonFee(int lessonFee) {
		this.lessonFee = lessonFee;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

}
