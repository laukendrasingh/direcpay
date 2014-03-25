package com.intelligrape.direcPay.command

enum DirecPayTransactionStatus {
    //TODO: Change delay interval
    SUCCESS("SUCCESS", null), FAIL("FAIL", null), TRANSACTION_BOOKED("Transaction Booked", 10), HOLD("Hold", 10),
    FUNDS_IN_CLEARING("Funds in Clearing", 10), TRANSACTION_REJECTED("Transaction Rejected", 10), WITDHRAWN("Withdrawn", 10)

    private String statusName
    private Integer delayInterval

    /**
     * For every transaction status we save delay interval in db for pulling the details
     * @param statusName
     * @param delayInterval as minute
     */
    private DirecPayTransactionStatus(String statusName, Integer delayInterval) {
        this.statusName = statusName
        this.delayInterval = delayInterval
    }

    public String getStatusName() {
        return this.statusName
    }

    public Integer getDelayInterval() {
        return this.delayInterval
    }
}

