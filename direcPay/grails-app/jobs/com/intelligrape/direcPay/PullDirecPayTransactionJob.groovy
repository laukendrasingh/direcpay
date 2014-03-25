package com.intelligrape.direcPay

import com.intelligrape.direcPay.common.DirecPayUtility
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
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
            log.debug "Execute PullDirecPayTransactionJob..."
            List<DirecPayCollection> pendingTransactions = direcPayService.pullPendingTransaction()
            pendingTransactions?.each {
                log.debug "pullPaymentDetails for direcPayReferenceId: ${it.direcPayReferenceId}"
                restCall(it)
            }
        } catch (Throwable throwable) {
            log.debug "Exception in excecuting job, ErrorMessage: ${throwable.message}"
        }

    }

    private static void restCall(DirecPayCollection collection) {
        log.debug("restCall for direcPayReferenceId: ${collection.direcPayReferenceId}")

        String url = DirecPayUtility.getDirecConfig("pull.transaction.details.URL")
        String merchantId = DirecPayUtility.getDirecConfig("merchantId")

        HttpClient httpClient = null
        try {
            String requestParameter = "${collection.direcPayReferenceId}|${merchantId}|${DirecPayUtility.getDirecConfig("return.transaction.details.URL")}"

            httpClient = HttpClientBuilder.create().build()
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader('requestParameter', requestParameter)
            HttpResponse httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.statusLine

            log.debug("httpClient: ${httpClient?.dump()}, httpPost: ${httpPost?.dump()}, httpResponse: ${httpResponse?.dump()}")

            int statusCode = statusLine?.statusCode
            if (statusCode != 200) {
                throw new RuntimeException("Non 200 response status received for ${url}. " +
                        "Status received is ${statusCode} with reason ${statusLine?.reasonPhrase}")
            }
            log.debug("response statusCode: ${statusCode}")
        } finally {
            httpClient?.close()
        }
    }

}
