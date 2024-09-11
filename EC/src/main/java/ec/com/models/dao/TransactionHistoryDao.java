package ec.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.models.entity.TransactionHistory;
@Repository
public interface TransactionHistoryDao extends JpaRepository<TransactionHistory, Long> {
	TransactionHistory save(TransactionHistory transactionHistory);
}
