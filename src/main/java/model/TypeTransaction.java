package model;

/**
 * This is an enum class. To specify the type of transaction.
 */
public enum TypeTransaction {
    /**
     * This type of transaction indicates that funds will be withdrawn from the account balance.
     */
    WITHDRAWALS,
    /**
     * This type of transaction indicates that there will be a replenishment of the account balance.
     */
    REPLENISHMENT,
    /**
     * This type of transaction indicates that funds will be transferred to another account.
     */
    TRANSFER_TO_CARD,
    /**
     * This type of transaction indicates that funds will be replenished from another account.
     */
    REPLENISHMENT_FROM
}
