package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility

class PaymentResponseCommand {
    String direcPayReferenceId
    DirecPayTransactionStatus transactionStatus
    String country
    String currency
    String otherDetails
    String merchantOrderNo
    double postingAmount

    /**
     *
     * @param inputString as a | separated string (1000001xxxxxxxxx|SUCCESS/FAIL|IND|INR|details|orderno|100|)
     * @return
     */
    public static PaymentResponseCommand populate(String responseString) {
        PaymentResponseCommand command = new PaymentResponseCommand()
        String[] array = DirecPayUtility.splitString(responseString)
        if (array) {
            command.direcPayReferenceId = array[0]
            command.transactionStatus = DirecPayTransactionStatus.valueOf(array[1])
            command.country = array[2]
            command.currency = array[3]
            command.otherDetails = array[4]
            command.merchantOrderNo = array[5]
            command.postingAmount = array[6] as double
        }
        return command
    }

}
