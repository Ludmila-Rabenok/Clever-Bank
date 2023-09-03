package service.impl;

import model.Check;
import model.Transaction;
import service.CheckService;
import service.PrintCheckService;
import util.ParsingFile;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class prepares a receipt for printing to a file.
 * The class contains a reference to an instance of the service class. And constants for the correct output of the check.
 */
public class PrintCheckServiceImpl implements PrintCheckService {
    private final CheckService checkService;
    private final ParsingFile parsingFile = new ParsingFile();

    private static final String TITLE = "Банковский чек";
    private static final String CHECK = "Чек:";
    private static final String TYPE_TRANSACTION = "Тип транзакции:";
    private static final String BANK = "Банк:";
    private static final String SENDER_BANK = "Банк отправителя:";
    private static final String RECIPIENT_BANK = "Банк получателя:";
    private static final String ACCOUNT = "Счет:";
    private static final String SENDER_ACCOUNT = "Счет отправителя:";
    private static final String RECIPIENT_ACCOUNT = "Счет получателя:";
    private static final String SUM = "Сумма:";
    private static final String TRANSFER = "Перевод";
    private static final String WITHDRAWAL = "Снятие средств";
    private static final String REPLENISHMENT = "Пополнение";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yy");
    private static final String FORMAT_35s = "%35s";
    private static final String FORMAT_22s_14d = "%-22s %14d";
    private static final String FORMAT_22s_14s = "%-22s %14s";
    private static final String FORMAT_22s_11s = "%-22s %11s";
    private static final String FORMAT_22s_10s_3s = "%-22s %10s %3s";
    private static final String DOUBLE_FORMAT = "#0.00";

    public PrintCheckServiceImpl(CheckService checkService) {
        this.checkService = checkService;
    }

    /**
     * This method is the manager.It calls the method{@link CheckServiceImpl#create(Transaction)} to create a check.
     * It calls the method{@link PrintCheckServiceImpl#prepareCheckForPrint(Transaction, Integer)} to prepare a check for printing.
     * It calls the method{@link ParsingFile#writeAll(List, Integer)} to write a check to a file.
     *
     * @param transaction contains all the necessary data for the receipt.
     */
    @Override
    public void saveCheckToFile(Transaction transaction) {
        Check check = checkService.create(transaction);
        List<String> checkList = prepareCheckForPrint(transaction, check.getId());
        parsingFile.writeAll(parsingFile.prepareForExport(checkList), check.getId());
    }

    /**
     * This method prepares a receipt depending on the type. And saves all rows in a List. And returns this List.
     * The method calls {@link PrintCheckServiceImpl#formatForTransfers(String, String, Long, Long)} ,
     * {@link PrintCheckServiceImpl#formatForWithdrawalsAndReplenishment(String, String, Long)}
     * depending on the transaction type.
     */
    @Override
    public List<String> prepareCheckForPrint(Transaction transaction, Integer idCheck) {
        List<String> list = new ArrayList<>();
        String timeFormat = TIME_FORMATTER.format(transaction.getDateTime());
        String dateFormat = DATE_FORMATTER.format(transaction.getDateTime());
        list.add(String.format(FORMAT_35s, TITLE));
        list.add(String.format(FORMAT_22s_14d, CHECK, idCheck));
        list.add(String.format(FORMAT_22s_11s, dateFormat, timeFormat));

        switch (transaction.getTypeTransaction()) {
            case WITHDRAWALS:
                list.addAll(formatForWithdrawalsAndReplenishment(WITHDRAWAL, getNameBank1(transaction),
                        getNumberAccount1(transaction)));
                break;
            case REPLENISHMENT:
                list.addAll(formatForWithdrawalsAndReplenishment(REPLENISHMENT, getNameBank1(transaction),
                        getNumberAccount1(transaction)));
                break;
            case TRANSFER_TO_CARD:
                list.addAll(formatForTransfers(getNameBank1(transaction), getNameBank2(transaction),
                        getNumberAccount1(transaction), getNumberAccount2(transaction)));
                break;
            case REPLENISHMENT_FROM:
                list.addAll(formatForTransfers(getNameBank2(transaction), getNameBank1(transaction),
                        getNumberAccount2(transaction), getNumberAccount1(transaction)));
                break;
            default:
                System.err.println("Неверный тип транзакции");
        }
        String formattedDoubleSum = new DecimalFormat(DOUBLE_FORMAT).format(transaction.getSum());
        list.add(String.format(FORMAT_22s_10s_3s, SUM, formattedDoubleSum, transaction.getAccount1().getCurrency()));
        return list;
    }

    String getNameBank1(Transaction transaction) {
        return transaction.getAccount1().getBank().getName();
    }

    Long getNumberAccount1(Transaction transaction) {
        return transaction.getAccount1().getNumber();
    }

    String getNameBank2(Transaction transaction) {
        return transaction.getAccount2().getBank().getName();
    }

    Long getNumberAccount2(Transaction transaction) {
        return transaction.getAccount2().getNumber();
    }

    @Override
    public List<String> formatForWithdrawalsAndReplenishment(String type, String bankName, Long numberAccount) {
        List<String> list = new ArrayList<>();
        list.add(String.format(FORMAT_22s_14s, TYPE_TRANSACTION, type));
        list.add(String.format(FORMAT_22s_14s, BANK, bankName));
        list.add(String.format(FORMAT_22s_14d, ACCOUNT, numberAccount));
        return list;
    }

    @Override
    public List<String> formatForTransfers(String bankName1, String bankName2, Long numberAccount1, Long numberAccount2) {
        List<String> list = new ArrayList<>();
        list.add(String.format(FORMAT_22s_14s, TYPE_TRANSACTION, TRANSFER));
        list.add(String.format(FORMAT_22s_14s, SENDER_BANK, bankName1));
        list.add(String.format(FORMAT_22s_14s, RECIPIENT_BANK, bankName2));
        list.add(String.format(FORMAT_22s_14d, SENDER_ACCOUNT, numberAccount1));
        list.add(String.format(FORMAT_22s_14d, RECIPIENT_ACCOUNT, numberAccount2));
        return list;
    }

}
