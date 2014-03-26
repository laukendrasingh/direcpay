package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.PaymentRequestCommand
import com.intelligrape.direcPay.command.PaymentResponseCommand
import com.intelligrape.direcPay.command.RefundRequestCommand
import com.intelligrape.direcPay.command.RefundResponseCommand
import com.intelligrape.direcPay.common.DirecPayUtility

class DirecPayController {
    static allowedMethods = [index: "POST"]

    DirecPayService direcPayService

    /**
     * Make payment with DirecPayCollection
     * @param command as PaymentRequestCommand
     * @return redirect on success or failure url
     */
    def index(PaymentRequestCommand command) {
        log.debug("make payment\n command: ${command.dump()}")

        /*if (!command.validate()) {
            log.debug "Validation: ${command.validate()}"
            String requestURL = request.getHeader("referer")
            redirect(url: requestURL)
            return
        }*/

        String encryptRequestParameter = command.getEncryptedRequestParameter()
        String encryptBillingDetail = command.getEncryptedBillingDetail()
        String encryptShippingDetail = command.getEncryptedShippingDetail()
        String encryptedStoreDetails = command.getEncryptedStoreCardDetail()

        String direcPayURL = DirecPayUtility.getDirecConfig("URL")
        String merchantId = DirecPayUtility.getDirecConfig("merchantId")
        String loadingText = DirecPayUtility.getDirecConfig("loadingText")

        log.debug("Payment with merchantId: ${merchantId} and direcPayURL: ${direcPayURL}")

        render(view: 'index', model: [direcPayURL: direcPayURL, requestparameter: encryptRequestParameter, billingDtls: encryptBillingDetail, shippingDtls: encryptShippingDetail, merchantId: merchantId, storeDtls: encryptedStoreDetails, isStoreCard: (command.customerId ? true : false), loadingText: loadingText])
    }

    def returnPaymentDetails() {
        log.debug("Return payment details,\nparams: ${params.dump()}")
        PaymentResponseCommand command = new PaymentResponseCommand(params?.responseparams)
        direcPayService.update(command)
        render(view: 'empty')
    }

    def refund(RefundRequestCommand command) {
        println"........................"
        println("refund for RefundRequestCommand: ${command.dump()}")
        DirecPayRefund refund = direcPayService.initRefund(command)
        String direcPayRefundURL = DirecPayUtility.getConfig("direcPay.refund.URL")
        command.refundRequestId = refund.id
        String requestParams = command.getEncryptedRequestParameter()
        log.debug("Refund,\ndirecPayRefundURL: ${direcPayRefundURL}, requestparams: ${requestParams}")
        render(view: 'refund', model: [direcPayRefundURL: direcPayRefundURL, requestparams: requestParams, merchantId: command.merchantId])
    }

    def responseRefundURL() {
        println"........................"
        println("Refund response,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        RefundResponseCommand command = new RefundResponseCommand(params.requestparams)
        direcPayService.updateRefund(command)
        render(view: 'empty')
    }

}




