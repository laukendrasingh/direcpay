package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility
import grails.validation.Validateable

@Validateable
class PaymentResponseCommand {
    String direcPayReferenceId //= "1001403000365347"
    DirecPayTransactionStatus transactionStatus
    String country
    String currency
    String otherDetails
    String merchantOrderNo
    Double postingAmount

    /**
     * @param inputString as a | separated string (1000001xxxxxxxxx|SUCCESS/FAIL|IND|INR|details|orderno|100|)
     * @return
     */
    PaymentResponseCommand(String responseString) {
        String[] array = DirecPayUtility.splitString(responseString)
        if (array) {
            direcPayReferenceId = array[0]
            transactionStatus = DirecPayTransactionStatus.valueOf(array[1])
            country = array[2]
            currency = array[3]
            otherDetails = array[4]
            merchantOrderNo = array[5]
            postingAmount = array[6] as Double
        }
    }

}
