package com.intelligrape.direcPay.command

enum DirecPayTransactionStatus {
    //Todo: Change pull interval
    SUCCESS("SUCCESS", null), FAIL("FAIL", null), TRANSACTION_BOOKED("Transaction Booked", 1000), HOLD("Hold", 1000),
    FUNDS_IN_CLEARING("Funds in Clearing", 1000), TRANSACTION_REJECTED("Transaction Rejected", 1000), WITDHRAWN("Withdrawn", 1000)

    private String statusName
    private Integer pullInterval

    private DirecPayTransactionStatus(String statusName, Integer pullInterval) {
        this.statusName = statusName
        this.pullInterval = pullInterval
    }

    public String getStatusName() {
        return this.statusName
    }

    public Integer getPullInterval() {
        return this.pullInterval
    }
}

