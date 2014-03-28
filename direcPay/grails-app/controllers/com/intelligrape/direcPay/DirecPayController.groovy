package com.intelligrape.direcPay

import com.intelligrape.direcPay.command.*
import com.intelligrape.direcPay.common.DirecPayUtility
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.methods.PostMethod

//TODO:Use log inplace of println
class DirecPayController {
    static allowedMethods = [index: "POST"]

    DirecPayService direcPayService

    /**
     * Make payment with DirecPayCollection
     * @param command as PaymentRequestCommand
     * @return redirect on success or failure url
     */
    def index(PaymentRequestCommand command) {
        log.debug("Start make payment, params: ${command.dump()}")

        String encryptRequestParameter = command.getEncryptedRequestParameter()
        String encryptBillingDetail = command.getEncryptedBillingDetail()
        String encryptShippingDetail = command.getEncryptedShippingDetail()
        String encryptedStoreDetails = command.getEncryptedStoreCardDetail()

        String direcPayURL = DirecPayUtility.getDirecConfig("URL")
        String merchantId = DirecPayUtility.getDirecConfig("merchantId")
        String loadingText = DirecPayUtility.getDirecConfig("loadingText")

        log.debug("MerchantId: ${merchantId} and DirecPayURL: ${direcPayURL}")

        render(view: 'index', model: [direcPayURL: direcPayURL, requestparameter: encryptRequestParameter, billingDtls: encryptBillingDetail, shippingDtls: encryptShippingDetail, merchantId: merchantId, storeDtls: encryptedStoreDetails, isStoreCard: (command.customerId ? true : false), loadingText: loadingText])
    }

    def refund(RefundRequestCommand command) {
        println("Start refund, params: ${command.dump()}")
        DirecPayRefund refund = direcPayService.initRefund(command)
//        String direcPayRefundURL = DirecPayUtility.getConfig("direcPay.refund.URL")
        command.refundRequestId = refund.id
        sendRefundRequest(command)
//        String requestParams = command.getEncryptedRequestParameter()
//        println("DirecPayRefundURL: ${direcPayRefundURL}, RequestParams: ${requestParams}, MerchantId: ${command.merchantId}")
//        render(view: 'refund', model: [direcPayRefundURL: direcPayRefundURL, requestparams: requestParams, merchantId: command.merchantId])
    }

    def responseRefundURL() {
        println("Get refund response, params: ${params.dump()}")
        RefundResponseCommand command = new RefundResponseCommand(params.responseparams)
        direcPayService.updateRefund(command)
        String redirectURL = command?.refundResponseStatus == RefundResponseStatus.SUCCESS ? DirecPayUtility.getConfig("success.refund.URL") : DirecPayUtility.getConfig("failre.refund.URL")
        redirect(url: redirectURL, params: [params: command])
//        render(view: 'empty')
    }

    def returnPaymentDetails() {
        println("Returning payment details, params: ${params.dump()}")
        PaymentResponseCommand command = new PaymentResponseCommand(params?.responseparams)
        direcPayService.updateCollection(command)
        render(view: 'empty')
    }

    //todo:remove this action and also direcPayPullTransactionDetails.gsp, these are only for testing
    def pullPaymentDetails(DirecPayCollection collection) {
        String requestParameter = "${collection.direcPayReferenceId}|${DirecPayUtility.getDirecConfig("merchantId")}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"
        println "PullPaymentDetails, requestparams: ${requestParameter}"
//        render(view: 'direcPayPullTransactionDetails', model: [requestparams: requestParameter, loadingText: DirecPayUtility.getDirecConfig("loadingText"), direcPayPullTransactionDetailsURL: DirecPayUtility.getDirecConfig("pull.transaction.details.URL")])
        sendRequest(requestParameter)
        render(view: 'empty')
    }

    private static void sendRefundRequest(RefundRequestCommand command) {
        PostMethod postMethod = null
        String resp = null
        try {
            String url = DirecPayUtility.getConfig("direcPay.refund.URL")
            String requestParams = command.getEncryptedRequestParameter()
            println("DirecPayRefundURL: ${url}, RequestParams: ${requestParams}, MerchantId: ${command.merchantId}")
            HttpClient httpClient = new HttpClient();
            postMethod = new PostMethod(url);
            postMethod.addParameter("requestparams", requestParams);
            postMethod.addParameter("merchantId", "" + command.merchantId);
            httpClient.executeMethod(postMethod);
        } catch (Exception e) {
            e.printStackTrace();
            println "Error in sending request, Message: ${e.message}"
        }

        if (postMethod?.statusCode == HttpStatus.SC_OK) {
            resp = postMethod.getResponseBodyAsString();
        } else {
            postMethod.getStatusLine();
        }
        println "Get sending request response :${resp}, StatusCode: ${postMethod.getStatusCode()}"
    }

    //todo:remove this action only for testing
    private static void sendRequest(String requestParameter) {
        PostMethod postMethod = null
        String resp = null
        try {
            String url = DirecPayUtility.getDirecConfig("pull.transaction.details.URL")
            HttpClient httpClient = new HttpClient();
            postMethod = new PostMethod(url);
            postMethod.addParameter("requestparams", requestParameter);
            httpClient.executeMethod(postMethod);
        } catch (Exception e) {
            e.printStackTrace();
            println "Error in sending request, Message: ${e.message}"
        }

        if (postMethod?.statusCode == HttpStatus.SC_OK) {
            resp = postMethod.getResponseBodyAsString();
        } else {
            postMethod.getStatusLine();
        }
        println "Get sending request response :${resp}, StatusCode: ${postMethod.getStatusCode()}"
    }

}




