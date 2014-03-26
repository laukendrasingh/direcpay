package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.*
import com.intelligrape.direcPay.command.DirecPayProgressStatus as PS
import com.intelligrape.direcPay.command.DirecPayTransactionStatus as TS

abstract class DirecPayTransaction {
    Long id
    Long version
    Date dateCreated
    Date lastUpdated

    String direcPayReferenceId
    String merchantOrderNo
    DirecPayTransactionStatus transactionStatus
    DirecPayProgressStatus progressStatus = PS.INITIATED
    DirecPaypPaymentStatus paymentStatus = returnPaymentStatus()
    Double amount

    static constraints = {
        amount nullable: false
        transactionStatus nullable: true
    }

    void updateProgressStatus(DirecPayTransactionStatus status) {
        this.progressStatus = status?.equals(TS.SUCCESS) || status?.equals(TS.FAIL) ? PS.PROCESSED : PS.AWAITED
    }

    void updateTransactionStatus(RefundResponseCommand command) {
        RefundResponseStatus status = command.refundResponseStatus
        this.transactionStatus = status.equals(RefundResponseStatus.SUCCESS) ? DirecPayTransactionStatus.SUCCESS : DirecPayTransactionStatus.FAIL
    }

    abstract DirecPaypPaymentStatus returnPaymentStatus();

}

