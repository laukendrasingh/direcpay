package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility
import grails.validation.Validateable

@Validateable
class RefundRequestCommand {
    String refundRequestId = System.currentTimeMillis() //It should be unique for each refund request from Merchants.
    String direcPayReferenceId //= "1001403000366135"
    long merchantId = DirecPayUtility.getConfig("direcPay.merchantId") as long
    String merchantOrderNo = "1394783769152"
    double refundAmount
//    String responseURL = DirecPayUtility.getConfig("direcPay.response.refund.URL")
    String responseURL = "http://direcpay.qa3.intelligrape.net/direcPay/responseRefundURL"

    //    RefundReqId|direcpayreferenceid|merchantId|orderid|refundamount|responseurl
    public String getEncryptedRequestParameter() {
        String requestParameter = "${refundRequestId}|${direcPayReferenceId}|${merchantId}|${merchantOrderNo}|${refundAmount}|${responseURL}"
        return DirecPayUtility.encrypt(requestParameter);
    }
}
