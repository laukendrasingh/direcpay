package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPaypPaymentStatus

class DirecPayRefund extends DirecPayTransaction {

    String message

    static constraints = {
        message nullable: true
    }

    @Override
    DirecPaypPaymentStatus returnPaymentStatus() {
        return DirecPaypPaymentStatus.REFUND
    }
}
