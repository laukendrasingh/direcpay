package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayProgressStatus as PS
import com.intelligrape.direcPay.command.DirecPayTransactionStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus as TS
import com.intelligrape.direcPay.command.DirecPaypPaymentStatus
import com.intelligrape.direcPay.command.PaymentResponseCommand

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

    static constraints = {

    }

    void updateProgressStatus(PaymentResponseCommand command) {
        DirecPayTransactionStatus status = command.transactionStatus
        this.progressStatus = status?.equals(TS.SUCCESS) || status?.equals(TS.FAIL) ? PS.PROCESSED : PS.AWAITED
    }

    abstract DirecPaypPaymentStatus returnPaymentStatus();

}
