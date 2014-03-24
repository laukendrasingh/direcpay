package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayProgressStatus as PS
import com.intelligrape.direcPay.command.DirecPayTransactionStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus as TS
import com.intelligrape.direcPay.command.DirecPaypPaymentStatus
import com.intelligrape.direcPay.command.PaymentResponseCommand
import org.grails.datastore.mapping.query.api.Criteria

class DirecPayCollection extends DirecPayTransaction {
//    Long id
//    Long version
//    Date dateCreated
//    Date lastUpdated

//    String direcPayReferenceId
//    String merchantOrderNo
//    DirecPayTransactionStatus transactionStatus
//    DirecPayProgressStatus progressStatus = PS.PROCESSED
//    DirecPaypPaymentStatus paymentStatus

    String otherDetails
    Long delayInterval //in minute
    Date nextExpectedUpdate

    static constraints = {
        otherDetails(nullable: true)
        delayInterval(nullable: true)
        nextExpectedUpdate(nullable: true)
    }

    static mapping = {
        nextExpectedUpdate formula: "DATE_SUB(now(), Interval delay_interval MINUTE)"
    }

    void updateProgressStatus(DirecPayTransactionStatus status) {
        this.progressStatus = status?.equals(TS.SUCCESS) || status?.equals(TS.FAIL) ? PS.PROCESSED : PS.AWAITED
    }

    void updateProperties(PaymentResponseCommand command) {
        super.direcPayReferenceId = command.direcPayReferenceId
        super.merchantOrderNo = command.merchantOrderNo
        super.transactionStatus = command.transactionStatus
        this.otherDetails = command.otherDetails
        updateProgressStatus(command.transactionStatus)
        this.delayInterval = transactionStatus.pullInterval

        this.save(flush: true)
    }

    @Override
    DirecPaypPaymentStatus returnPaymentStatus() {
        return DirecPaypPaymentStatus.COLLECTION
    }

    static List<DirecPayCollection> pullPendingTransactions() {
//        log.debug("Pulling pending transactions")
        /*Criteria criteria = createCriteria()
        List<DirecPayCollection> list = criteria.list {
            eq('progressStatus', DirecPayProgressStatus.AWAITED)
            leProprty("lastUpdated", "nextExpectedUpdate")
        }
        return list*/
    }
}
