package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus as TS
import com.intelligrape.direcPay.command.PaymentResponseCommand
import com.intelligrape.direcPay.command.RefundRequestCommand
import com.intelligrape.direcPay.command.RefundResponseCommand
import grails.transaction.Transactional

//TODO:Use log inplace of println
@Transactional
class DirecPayService {

    /**
     * Save transactions response
     * @param params as success response params
     */
    void save(PaymentResponseCommand command) {
        println("Save make transaction response, params: ${command?.dump()}")

        if (command) {
            DirecPayCollection collection = new DirecPayCollection()
            collection.updateProperties(command)
            println "Is valid collection: ${collection.validate()}, Collection: ${collection.dump()}"
            DirecPayCollection savedCollection = collection.save(flush: true)
            println "Collection successfully saved, result: ${savedCollection}"
        }
    }

    /**
     * Update transactions response
     * @param params as success response params
     */
    void updateCollection(PaymentResponseCommand command) {
        println("Update collection, params: ${command.dump()}")
        if (command) {
            DirecPayCollection collection = DirecPayCollection.findByDirecPayReferenceIdAndMerchantOrderNoAndTransactionStatusNotInList(command.direcPayReferenceId, command.merchantOrderNo, [TS.SUCCESS, TS.FAIL])
            collection?.updateProperties(command)
        }
    }

    List<DirecPayCollection> pullPendingTransaction() {
        println("Pull pending transaction")
        List<DirecPayCollection> list = DirecPayCollection.pullPendingTransactions()
        return list
    }

    DirecPayRefund initRefund(RefundRequestCommand command) {
        println("Init refund, params: ${command.dump()}")
        DirecPayRefund refund = new DirecPayRefund(direcPayReferenceId: command.direcPayReferenceId, merchantOrderNo: command.merchantOrderNo, amount: command.refundAmount, progressStatus: DirecPayProgressStatus.INITIATED)
        println("Refund: ${refund.dump()}, is valid refund: ${refund.validate()}, Error: ${refund.errors?.dump()}")
        return refund.save(flush: true, failOnError: true)
    }

    DirecPayRefund updateRefund(RefundResponseCommand command) {
        println("update Refund, params: ${command.dump()}")
        DirecPayRefund refund = null
        if (command) {
            refund = DirecPayRefund.findByDirecPayReferenceIdAndProgressStatus(command.direcPayReferenceId, DirecPayProgressStatus.INITIATED) //todo needs to confirm with support for this, should check with DirecPayRefundId
            try {
                refund.progressStatus = DirecPayProgressStatus.PROCESSED
                refund.updateTransactionStatus(command)
                refund.message = command.message
                refund.save(flush: true)
            } catch (Exception e) {
                println("Refund not found")
                throw new Exception("DirecPay not found, ErrorMessage: ${e.message}")
            }
        }
        return refund
    }

}














