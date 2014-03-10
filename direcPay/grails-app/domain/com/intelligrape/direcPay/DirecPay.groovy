package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus

class DirecPay {
    Long id
    Long version
    Date dateCreated
    Date lastUpdated

    String direcPayReferenceId
    String merchantOrderNo
    DirecPayTransactionStatus transactionStatus
    String otherDetails
    DirecPayProgressStatus progressStatus = DirecPayProgressStatus.PROCESSED

    static constraints = {
        otherDetails(nullable: true)
    }
}
