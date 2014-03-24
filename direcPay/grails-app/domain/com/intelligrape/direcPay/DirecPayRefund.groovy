package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayTransactionStatus
import com.intelligrape.direcPay.command.DirecPaypPaymentStatus
import com.intelligrape.direcPay.command.RefundResponseCommand
import com.intelligrape.direcPay.command.RefundResponseStatus

class DirecPayRefund extends DirecPayTransaction {

    String message

    @Override
    DirecPaypPaymentStatus returnPaymentStatus() {
        return DirecPaypPaymentStatus.REFUND
    }

    void updateTransactionStatus(RefundResponseCommand command) {
        RefundResponseStatus status = command.refundResponseStatus
        this.transactionStatus = status.equals(RefundResponseStatus.SUCCESS) ? DirecPayTransactionStatus.SUCCESS : DirecPayTransactionStatus.FAIL
    }
}
