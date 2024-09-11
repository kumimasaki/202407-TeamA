package ec.com.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TransactionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long lessonId;
	private Long transactionId;

	public TransactionItem() {
	}

	public TransactionItem(Long lessonId, Long transactionId) {
		this.lessonId = lessonId;
		this.transactionId = transactionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

}
