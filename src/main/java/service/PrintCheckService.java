package service;

import model.Transaction;

import java.util.List;

public interface PrintCheckService {

    void saveCheckToFile(Transaction transaction);

    List<String> prepareCheckForPrint(Transaction transaction, Integer idCheck);

    List<String> formatForWithdrawalsAndReplenishment(String type, String bankName, Long numberAccount);

    List<String> formatForTransfers(String bankName1, String bankName2, Long numberAccount1, Long numberAccount2);

}
