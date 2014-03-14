package com.intelligrape.direcPay

import com.intelligrape.direcPay.common.DirecPayUtility

class PullDirecPayTransactionJob {
    DirecPayService direcPayService
    DirecPayController controller

    static triggers = {
        simple repeatInterval: DirecPayUtility.getConfig("direcPay.pull.transaction.job.interval")
    }

    def execute() {
        //todo it should pull all awaited transactions and send one by one using RestClient and each update should be transactional and handle exceptions and flow should not stop if one fails
        try {
            log.debug "Execute PullDirecPayTransactionJob..."
            List<DirecPayCollection> pendingTransactions = direcPayService.pullPendingTransaction()
            pendingTransactions?.each {
                controller.pullPaymentDetails(it)
            }
        } catch (Throwable throwable) {
            log.debug "Exception in excecuting job, ErrorMessage: ${throwable.message}"
        }

    }
}
