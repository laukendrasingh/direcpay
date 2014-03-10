package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.PaymentRequestCommand
import com.intelligrape.direcPay.common.DirecPayUtility

class DirecPayController {
//    static allowedMethods = [index: "POST"]

    DirecPayService direcPayService

    /**
     * Make payment with DirecPay
     * @param command as PaymentRequestCommand
     * @return redirect on success or failure url
     */
    def index(PaymentRequestCommand command) {

        println("make payment.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}, \nrequest: ${request.dump()}")

        /*if (!command.validate()) {
            log.debug "Validation: ${command.validate()}, Parameter: ${command.dump()}"
            String requestURL = request.getHeader("referer")
            redirect(url: requestURL)
            return
        }*/

        String encryptRequestParameter = command.getEncryptedRequestParameter()
        String encryptBillingDetail = command.getEncryptedBillingDetail()
        String encryptShippingDetail = command.getEncryptedShippingDetail()
        String encryptedStoreDetails = command.getEncryptedStoreCardDetail()

        String direcPayURL = DirecPayUtility.getConfig("direcPay.URL")
        String merchantId = DirecPayUtility.getConfig("direcPay.merchantId")
        String loadingText = DirecPayUtility.getConfig("direcPay.loadingText")

        println("Payment with merchantId: ${merchantId} and direcPayURL: ${direcPayURL}")

        render(view: 'index', model: [direcPayURL: direcPayURL, requestparameter: encryptRequestParameter, billingDtls: encryptBillingDetail, shippingDtls: encryptShippingDetail, merchantId: merchantId, storeDtls: encryptedStoreDetails, isStoreCard: (command.customerId ? true : false), loadingText: loadingText])
    }

    //TODO: need to fix
    def pullPaymentDetails() {
//        String requestparams = "1001403000365347|${DirecPayUtility.getConfig("direcPay.merchantId")}|${DirecPayUtility.getConfig("direcPay.return.transaction.details.URL")}"
        String requestparams = "1001403000365347|${DirecPayUtility.getConfig("direcPay.merchantId")}|http://localhost:8080/DirecPayTest/returnPaymentDetails"
        log.debug "pullPaymentDetails, requestparams: ${requestparams}"
        render(view: 'direcPayPullTransactionDetails', model: [requestparams: requestparams, loadingText: DirecPayUtility.getConfig("direcPay.loadingText"), direcPayPullTransactionDetailsURL: DirecPayUtility.getConfig("direcPay.pull.transaction.details.URL")])
    }

    //TODO: need to fix
    def returnPaymentDetails() {
        println("returnPaymentDetails.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        direcPayService.update(params.requestparams)
        render(view: 'returnPaymentDetails')
    }

}




