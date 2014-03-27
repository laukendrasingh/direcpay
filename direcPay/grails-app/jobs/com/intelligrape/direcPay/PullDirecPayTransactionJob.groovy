package com.intelligrape.direcPay

import com.intelligrape.direcPay.common.DirecPayUtility
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.httpclient.methods.PostMethod
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PullDirecPayTransactionJob {
    private static final Logger log = LoggerFactory.getLogger(this)
    DirecPayService direcPayService

    static triggers = {
        simple repeatInterval: DirecPayUtility.getConfig("direcPay.pull.transaction.job.interval")
    }

    def execute() {
        try {
            log.debug "Executing pull direcPay transaction job..."
            List<DirecPayCollection> pendingTransactions = direcPayService.pullPendingTransaction()
            pendingTransactions?.each {
                log.debug "Sending request for pullPaymentDetails, ReferenceId: ${it.direcPayReferenceId}"
                sendRequest(it)
            }
        } catch (Throwable throwable) {
            log.debug "Exception in excecuting job, Message: ${throwable.message}"
        }

    }

    private static void sendRequest(DirecPayCollection collection) {
        PostMethod postMethod = null
        String resp = null
        try {
            String url = DirecPayUtility.getDirecConfig("pull.transaction.details.URL")
            String merchantId = DirecPayUtility.getDirecConfig("merchantId")
            String requestParameter = "${collection.direcPayReferenceId}|${merchantId}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"

            println("Sending request, Collection: ${collection.dump()}, URL: ${url}, MerchantId: ${merchantId}, RequestParameter: ${requestParameter}")

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
