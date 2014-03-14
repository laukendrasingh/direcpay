package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility

class RefundResponseCommand {
    String direcPayReferenceId
    RefundResponseStatus refundResponseStatus
    String message

    RefundResponseCommand(String responseString){
        String[] array = DirecPayUtility.splitString(responseString)
        if (array) {
            direcPayReferenceId = array[0]
            refundResponseStatus = RefundResponseStatus.valueOf(array[1])
            message = array[2]
        }
    }

    /**
     *
     * @param inputString as a | separated string (1000001xxxxxxxxx|SUCCESS|Refund Request Successfully Posted)
     * @return
     */
    public static RefundResponseCommand populate(String responseString) {
        RefundResponseCommand command = new RefundResponseCommand()
        String[] array = DirecPayUtility.splitString(responseString)
        if (array) {
            command.direcPayReferenceId = array[0]
            command.refundResponseStatus = RefundResponseStatus.valueOf(array[1])
            command.message = array[2]
        }
        return command
    }
}

enum RefundResponseStatus {
    SUCCESS, ERROR
}
