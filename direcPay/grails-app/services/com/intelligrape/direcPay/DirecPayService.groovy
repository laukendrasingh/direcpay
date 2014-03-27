package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.PaymentResponseCommand
import com.intelligrape.direcPay.command.RefundRequestCommand
import com.intelligrape.direcPay.command.RefundResponseCommand
import grails.transaction.Transactional

@Transactional
class DirecPayService {

    /**
     * Save transactions response
     * @param params as success response params
     */
    void save(PaymentResponseCommand command) {
        println("save transaction response, command: ${command?.dump()}")

        if (command) {
            DirecPayCollection direcPay = new DirecPayCollection()
            direcPay.updateProperties(command)
            println "DirecPayCollection validation: ${direcPay.validate()}, direcPay: ${direcPay.dump()}"
            DirecPayCollection result = direcPay.save(flush: true)
            println "DirecPayCollection successfully saved, result: ${result}"
        }
    }

    /**
     * Update transactions response
     * @param params as success response params
     */
    void update(PaymentResponseCommand command) {
        println("update transaction response, command: ${command.dump()}")
        if (command) {
            DirecPayCollection direcPay = DirecPayCollection.findByDirecPayReferenceIdAndMerchantOrderNo(command.direcPayReferenceId, command.merchantOrderNo)
            direcPay?.updateProperties(command)
        }
    }

    List<DirecPayCollection> pullPendingTransaction() {
        println("Pulling pending transaction")
//todo::        List<DirecPayCollection> list = DirecPayCollection.pullPendingTransactions()
        List<DirecPayCollection> list = DirecPayCollection.all
        return list
    }

    DirecPayRefund initRefund(RefundRequestCommand command) {
        println("initRefund for direcPayReferenceId: ${command.direcPayReferenceId}")
        DirecPayRefund refund = new DirecPayRefund(direcPayReferenceId: command.direcPayReferenceId, merchantOrderNo: command.merchantOrderNo, amount: command.refundAmount, progressStatus: DirecPayProgressStatus.INITIATED)
        println("refund: ${refund.dump()}, refund.validate: ${refund.validate()}, error: ${refund.errors?.dump()}")
        refund.save(flush: true)
        return refund
    }

    void updateRefund(RefundResponseCommand command) {
        println("updateRefund, params: ${command.dump()}")
        if (command) {
            DirecPayRefund refund = DirecPayRefund.findByDirecPayReferenceId(command.direcPayReferenceId) //todo needs to confirm with support for this, should check with DirecPayRefundId
            try {
                refund.progressStatus = DirecPayProgressStatus.PROCESSED
                refund.updateTransactionStatus(command)
                refund.message = command.message
                refund.save()
            } catch (Exception e) {
                throw new Exception("DirecPay not found, ErrorMessage: ${e.message}")
            }
        }
    }

}














