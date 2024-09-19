//package teama.com.services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import teama.com.models.entity.TransactionHistory;
//
//@Service
//public class TransactionHistoryService {
//
//    @Autowired
//    private TransactionHistoryRepository transactionHistoryRepository;
//
//    // 根据用户ID获取购买历史
//    public List<TransactionHistory> getTransactionHistoryByUser(Long userId) {
//        return transactionHistoryRepository.findByUserId(userId);
//    }
//}