package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus
import com.intelligrape.direcPay.command.DirecPaypPaymentStatus
import com.intelligrape.direcPay.command.PaymentResponseCommand
import org.grails.datastore.mapping.query.api.Criteria

//TODO:Use log inplace of println
class DirecPayCollection extends DirecPayTransaction {
    String otherDetails
    Integer delayInterval //in minute
    Date nextExpectedUpdate

    static constraints = {
        otherDetails(nullable: true)
        delayInterval(nullable: true)
        nextExpectedUpdate(nullable: true)
    }

    static mapping = {
//TODO:Need to fix following formula
//      nextExpectedUpdate formula: "DATE_SUB(now(), Interval delay_interval MINUTE)"
        nextExpectedUpdate formula: "now()"
    }

    void updateProperties(PaymentResponseCommand command) {
        this.properties = command.properties
//        this.direcPayReferenceId = command.direcPayReferenceId
//        this.merchantOrderNo = command.merchantOrderNo
//        this.transactionStatus = command.transactionStatus
        updateProgressStatus(command.transactionStatus)
//        this.paymentStatus = returnPaymentStatus()
        this.amount = command.postingAmount
//        this.otherDetails = command.otherDetails
        this.delayInterval = transactionStatus?.delayInterval
    }

    @Override
    DirecPaypPaymentStatus returnPaymentStatus() {
        return DirecPaypPaymentStatus.COLLECTION
    }

    static List<DirecPayCollection> pullPendingTransactions() {
        println("Pulling pending transactions")
        Criteria criteria = createCriteria()
        List<DirecPayCollection> list = criteria.list {
            eq('progressStatus', DirecPayProgressStatus.AWAITED)
            notEqual('transactionStatus', DirecPayTransactionStatus.SUCCESS)
            notEqual('transactionStatus', DirecPayTransactionStatus.FAIL)
            sqlRestriction("last_updated <= DATE_SUB(now(), INTERVAL delay_interval MINUTE)")
        }
        println "pullPendingTransactions: ${list.dump()}"
        return list
    }
}
