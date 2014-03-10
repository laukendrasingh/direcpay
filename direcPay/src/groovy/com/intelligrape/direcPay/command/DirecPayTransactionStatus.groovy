package com.intelligrape.direcPay.command

enum DirecPayTransactionStatus {
    SUCCESS("SUCCESS"), FAIL("FAIL"), TRANSACTION_BOOKED("Transaction Booked"), HOLD("Hold"),
    FUNDS_IN_CLEARING("Funds in Clearing"), TRANSACTION_REJECTED("Transaction Rejected"), WITDHRAWN("Withdrawn")

    private String statusName

    private DirecPayTransactionStatus(String statusName) {
        this.statusName = statusName
    }

    public String getStatusName() {
        return this.statusName
    }
}

