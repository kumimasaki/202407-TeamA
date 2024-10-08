package teama.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import teama.com.models.entity.TransactionItem;
@Repository
public interface TransactionItemDao extends JpaRepository<TransactionItem, Long> {
	TransactionItem save(TransactionItem transactionItem);
}
