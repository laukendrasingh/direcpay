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
        super.direcPayReferenceId = command.direcPayReferenceId
        super.merchantOrderNo = command.merchantOrderNo
        super.transactionStatus = command.transactionStatus
        updateProgressStatus(command.transactionStatus)
        super.paymentStatus = returnPaymentStatus()
        super.amount = command.postingAmount
        this.otherDetails = command.otherDetails
        this.delayInterval = transactionStatus?.delayInterval
    }

    @Override
    DirecPaypPaymentStatus returnPaymentStatus() {
        return DirecPaypPaymentStatus.COLLECTION
    }

    //TODO::need to fix criteria
    static List<DirecPayCollection> pullPendingTransactions() {
        Criteria criteria = createCriteria()
        /*Date date
        use(TimeCategory) {
            date = new Date() - this.delayInterval.minutes
        }*/
        List<DirecPayCollection> list = criteria.list {
            eq('progressStatus', DirecPayProgressStatus.AWAITED)
            notEqual('transactionStatus', DirecPayTransactionStatus.SUCCESS)
            notEqual('transactionStatus', DirecPayTransactionStatus.FAIL)
            le('lastUpdated', nextExpectedUpdate)
        }
        println "Pull pending transactions: ${list.dump()}"

        return all
    }
}
