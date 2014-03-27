package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.PaymentRequestCommand
import com.intelligrape.direcPay.command.PaymentResponseCommand
import com.intelligrape.direcPay.command.RefundRequestCommand
import com.intelligrape.direcPay.command.RefundResponseCommand
import com.intelligrape.direcPay.common.DirecPayUtility
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.methods.PostMethod

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

    def refund(RefundRequestCommand command) {
        println "........................"
        println("refund for RefundRequestCommand: ${command.dump()}")
        DirecPayRefund refund = direcPayService.initRefund(command)
        String direcPayRefundURL = DirecPayUtility.getConfig("direcPay.refund.URL")
        //todo:fixme
        command.refundRequestId = refund.id
        String requestParams = command.getEncryptedRequestParameter()
        println("Refund,\ndirecPayRefundURL: ${direcPayRefundURL}, requestparams: ${requestParams}, merchantId: ${command.merchantId}")
        render(view: 'refund', model: [direcPayRefundURL: direcPayRefundURL, requestparams: requestParams, merchantId: command.merchantId])
    }

    def responseRefundURL() {
        println "........................"
        println("Refund response,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        RefundResponseCommand command = new RefundResponseCommand(params.requestparams)
        direcPayService.updateRefund(command)
        render(view: 'empty')
    }

    def pullPaymentDetails(DirecPayCollection direcPayCollection) {
        String requestParameter = "${direcPayCollection.direcPayReferenceId}|${DirecPayUtility.getDirecConfig("merchantId")}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"
        println "PullPaymentDetails.........., requestparams: ${requestParameter}"
//        render(view: 'direcPayPullTransactionDetails', model: [requestparams: requestParameter, loadingText: DirecPayUtility.getDirecConfig("loadingText"), direcPayPullTransactionDetailsURL: DirecPayUtility.getDirecConfig("pull.transaction.details.URL")])
//        restCall(direcPayCollection)
        sendRequest(direcPayCollection)
        render(view: 'empty')
    }

    def returnPaymentDetails() {
        println "........................"
        println("Return payment details,\nparams: ${params.dump()}")
        PaymentResponseCommand command = new PaymentResponseCommand(params?.responseparams)
        println("command: ${command.dump()}")
        direcPayService.update(command)
        render(view: 'empty')
    }

    private static void sendRequest(DirecPayCollection collection) {
        PostMethod postMethod = null
        String resp = null
        try {
            String url = DirecPayUtility.getDirecConfig("pull.transaction.details.URL")
            String merchantId = DirecPayUtility.getDirecConfig("merchantId")
            String requestParameter = "${collection.direcPayReferenceId}|${merchantId}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"

            println("sendRequest, DirecPayCollection: ${collection.dump()}, URL: ${url}, MerchantId: ${merchantId}, RequestParameter: ${requestParameter}")

            HttpClient httpClient = new HttpClient();
            postMethod = new PostMethod(url);
            postMethod.addParameter("requestparams", requestParameter);
            httpClient.executeMethod(postMethod);
        } catch (Exception e) {
            e.printStackTrace();
            println "Error in send request, ErrorMessage: ${e.message}"
        }

        if (postMethod?.statusCode == HttpStatus.SC_OK) {
            resp = postMethod.getResponseBodyAsString();
        } else {
            postMethod.getStatusLine();
        }
        println "Response :${resp}, StatusCode: ${postMethod.getStatusCode()}"
    }

}




