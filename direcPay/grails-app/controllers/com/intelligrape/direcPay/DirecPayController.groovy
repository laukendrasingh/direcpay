package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.PaymentRequestCommand
import com.intelligrape.direcPay.command.PaymentResponseCommand
import com.intelligrape.direcPay.command.RefundRequestCommand
import com.intelligrape.direcPay.command.RefundResponseCommand
import com.intelligrape.direcPay.common.DirecPayUtility

//TODO:: Use log statements inplace of println
class DirecPayController {
    static allowedMethods = [index: "POST"]

    DirecPayService direcPayService

    /**
     * Make payment with DirecPayCollection
     * @param command as PaymentRequestCommand
     * @return redirect on success or failure url
     */
    def index(PaymentRequestCommand command) {
        println("make payment..........\n command: ${command.dump()}")

        /*if (!command.validate()) {
            println "Validation: ${command.validate()}"
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

        println("Payment with merchantId: ${merchantId} and direcPayURL: ${direcPayURL}")

        render(view: 'index', model: [direcPayURL: direcPayURL, requestparameter: encryptRequestParameter, billingDtls: encryptBillingDetail, shippingDtls: encryptShippingDetail, merchantId: merchantId, storeDtls: encryptedStoreDetails, isStoreCard: (command.customerId ? true : false), loadingText: loadingText])
//        redirect(url: direcPayURL, params: [requestparameter: encryptRequestParameter, billingDtls: encryptBillingDetail, shippingDtls: encryptShippingDetail, merchantId: merchantId, storeDtls: encryptedStoreDetails, isStoreCard: (command.customerId ? true : false), loadingText: loadingText])
    }

    def pullPaymentDetails(DirecPayCollection direcPayCollection) {
        String requestParameter = "${direcPayCollection.direcPayReferenceId}|${DirecPayUtility.getConfig("merchantId")}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"
        println "PullPaymentDetails.........., requestparams: ${requestParameter}"
//        render(view: 'direcPayPullTransactionDetails', model: [requestparams: requestParameter, loadingText: DirecPayUtility.getDirecConfig("loadingText"), direcPayPullTransactionDetailsURL: DirecPayUtility.getDirecConfig("pull.transaction.details.URL")])
        redirect(url: DirecPayUtility.getDirecConfig("pull.transaction.details.URL"), params: [requestparams: requestParameter])
    }

    def returnPaymentDetails() {
        println("ReturnPaymentDetails..........,\nparams: ${params.dump()}")
        PaymentResponseCommand command = new PaymentResponseCommand(params?.responseparams)
        direcPayService.update(command)
        render(view: 'returnPaymentDetails')
    }

    def refund(RefundRequestCommand command) {
        DirecPayRefund refund = direcPayService.initRefund(command)
        String direcPayRefundURL = DirecPayUtility.getConfig("direcPay.refund.URL")
        command.refundRequestId = refund.id
        String requestparams = command.getEncryptedRequestParameter()
        println("Refund..........,\ndirecPayRefundURL: ${direcPayRefundURL}, requestparams: ${requestparams}")
//        render(view: 'refund', model: [direcPayRefundURL: direcPayRefundURL, requestparams: requestparams, merchantId: command.merchantId])
        redirect(url: direcPayRefundURL, params: [requestparams: requestparams, merchantId: command.merchantId])
    }

    def responseRefundURL() {
        println("RefundResponseURL..........,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        RefundResponseCommand command = new RefundResponseCommand(params.requestparams)
        direcPayService.updateRefund(command)
        render(view: 'empty')
    }

}




