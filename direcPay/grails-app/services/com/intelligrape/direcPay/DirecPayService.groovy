package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus
import com.intelligrape.direcPay.command.PaymentResponseCommand
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class DirecPayService {

    /**
     * Save transactions response
     * @param params as success response params
     */
    PaymentResponseCommand save(GrailsParameterMap params) {
        log.debug("save transaction response, params: ${params}")
        PaymentResponseCommand command = null

        if (params && params.responseparams) {
            command = PaymentResponseCommand.populate(params?.responseparams)
            log.debug("save response for direcPayReferenceId: ${command?.direcPayReferenceId}, transactionStatus: ${command?.transactionStatus?.name()}, merchantOrderNo: ${command?.merchantOrderNo}")
            if (command) {
                DirecPay direcPay = new DirecPay(command?.properties)
                direcPay.progressStatus = command?.transactionStatus?.equals(DirecPayTransactionStatus.SUCCESS) || command?.transactionStatus?.equals(DirecPayTransactionStatus.FAIL) ? DirecPayProgressStatus.PROCESSED : DirecPayProgressStatus.AWAITED
                direcPay.save()
            }
        }
        return command
    }

    /**
     * Update transactions response
     * @param params as success response params
     */
    PaymentResponseCommand update(GrailsParameterMap params) {
        log.debug("update transaction response, params: ${params}")

        PaymentResponseCommand command = null
        if (params && params.responseparams) {
            command = PaymentResponseCommand.populate(params?.responseparams)
            log.debug("update response for direcPayReferenceId: ${command?.direcPayReferenceId}, transactionStatus: ${command?.transactionStatus?.name()}, merchantOrderNo: ${command?.merchantOrderNo}")

            DirecPay direcPay = DirecPay.findByDirecPayReferenceIdAndMerchantOrderNo(command.direcPayReferenceId, command.merchantOrderNo)
            direcPay?.properties = command?.properties
            direcPay?.progressStatus = command?.transactionStatus?.equals(DirecPayTransactionStatus.SUCCESS) || command?.transactionStatus?.equals(DirecPayTransactionStatus.FAIL) ? DirecPayProgressStatus.PROCESSED : DirecPayProgressStatus.AWAITED
            direcPay?.save()
        }
        return command
    }

}
