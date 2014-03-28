package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility
import grails.validation.Validateable

@Validateable
class RefundRequestCommand {
    String refundRequestId //=System.currentTimeMillis()//It should be unique for each refund request from Merchants.
    String direcPayReferenceId
    long merchantId = DirecPayUtility.getDirecConfig("merchantId") as long
    String merchantOrderNo
    double refundAmount

    //    RefundReqId|direcpayreferenceid|merchantId|orderid|refundamount|responseurl
    public String getEncryptedRequestParameter() {
        String requestParameter = "${refundRequestId}|${direcPayReferenceId}|${merchantId}|${merchantOrderNo}|${refundAmount}|${DirecPayUtility.getDirecConfig("response.refund.URL")}"
        println "requestParameter: ${requestParameter}"
        return DirecPayUtility.encrypt(requestParameter);
    }
}
