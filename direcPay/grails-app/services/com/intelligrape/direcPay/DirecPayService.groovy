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
        log.debug("save transaction response, command: ${command?.dump()}")

        if (command) {
            DirecPayCollection direcPay = new DirecPayCollection()
            direcPay.updateProperties(command)
        }
    }

    /**
     * Update transactions response
     * @param params as success response params
     */
    void update(PaymentResponseCommand command) {
        log.debug("update transaction response, command: ${command.dump()}")
        if (command) {
            DirecPayCollection direcPay = DirecPayCollection.findByDirecPayReferenceIdAndMerchantOrderNo(command.direcPayReferenceId, command.merchantOrderNo)
            direcPay?.updateProperties(command)
        }
    }

    List<DirecPayCollection> pullPendingTransaction() {
        log.debug("Pulling pending transaction")
        List<DirecPayCollection> list = DirecPayCollection.pullPendingTransactions()
        return list
    }

    DirecPayRefund initRefund(RefundRequestCommand command) {
        log.debug("initRefund for direcPayReferenceId: ${command.direcPayReferenceId}")
        DirecPayRefund refund = new DirecPayRefund(direcPayReferenceId: command.direcPayReferenceId, merchantOrderNo: command.merchantOrderNo, progressStatus: DirecPayProgressStatus.INITIATED)
        refund.save(flush: true)
        return refund
    }

    void updateRefund(RefundResponseCommand command) {
        log.debug("updateRefund, params: ${command.dump()}")
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














