package com.intelligrape.direcPay.command

import com.intelligrape.direcPay.common.DirecPayUtility
import grails.validation.Validateable

@Validateable
class PaymentRequestCommand {
    //RequestParameters: All fields are mandatory
    long merchantId = DirecPayUtility.getConfig("direcPay.merchantId") as long
    String operatingMode = DirecPayUtility.getConfig("direcPay.operatingMode") //fixed value DOM
    String country = "IND"  //Default value is IND
    String currency = "INR" //Default value is INR
    double amount = 10.00
    long merchantOrderNo = System.currentTimeMillis()   //no String
    String otherDetails //= "test others"
    String collaborator = DirecPayUtility.getConfig("direcPay.collaborator")   //fixed value this would be DirecPay in live mode
    String successURL = "http://localhost:8080/DirecPayTest/appSuccess"
    String failureURL = "http://localhost:8080/DirecPayTest/appFailure"

    //BillingDetail: All fields are mandatory (either mobile or phone is mandatory)
    String customerName //= "Loki"
    String customerAddress //= "Mumbai"
    String customerCity //= "Mumbai"
    String customerState //= "Maharashtra"
    int customerPinCode //= 400001
    String customerCountry = "IN"

    //<<---DirecPay does not hold following three field
    String customerPhoneNo1 //= "91"
    String customerPhoneNo2 //= "022"
    String customerPhoneNo3 //= "28000000"
    //--->

    String customerMobileNo = "9820000000"
    String customerEmailId = "testuser@gmail.com"
    String otherNotes //= "Laukendra test transaction for direcpay"

    //ShippingDetail: (either mobile or phone is mandatory)
    String deliveryName
    String deliveryAddress
    String deliveryCity
    String deliveryState
    int deliveryPinCode  //zipCode
    String deliveryCountry = "IN"
    String deliveryPhNo1 = "91"
    String deliveryPhNo2 = "022"
    String deliveryPhNo3 = "28000000"
    String deliveryMobileNo //= "9920000000"

    //For store card functionality
    String customerId

    static constraints = {
        //RequestParameters.....
        operatingMode maxSize: 3
        country maxSize: 3
        currency maxSize: 3
//        amount max: 7
//        merchantOrderNo max: 100
        otherDetails maxSize: 100, nullable: true
        collaborator maxSize: 8
        successURL maxSize: 200
        failureURL maxSize: 200

        //BillingDetail.....
        customerCountry maxSize: 2
        customerEmailId email: true, maxSize: 100
        customerMobileNo maxSize: 10

        customerName maxSize: 50, nullable: true
        customerAddress maxSize: 200, nullable: true
        customerCity maxSize: 50, nullable: true
        customerState maxSize: 50, nullable: true
        customerPinCode nullable: true
        customerPhoneNo1 maxSize: 3, nullable: true
        customerPhoneNo2 maxSize: 8, nullable: true
        customerPhoneNo3 maxSize: 9, nullable: true

        //ShippingDetail.....
        deliveryName maxSize: 50, nullable: true
        deliveryAddress maxSize: 200, nullable: true
        deliveryCity maxSize: 50, nullable: true
        deliveryState maxSize: 50, nullable: true
        deliveryPinCode nullable: true
        deliveryCountry maxSize: 2, nullable: true
        deliveryPhNo1 maxSize: 3, nullable: true
        deliveryPhNo2 maxSize: 8, nullable: true
        deliveryPhNo3 maxSize: 9, nullable: true
        deliveryMobileNo maxSize: 10, nullable: true
        otherNotes maxSize: 200, nullable: true

        //For store card functionality
        customerId maxSize: 16, nullable: true, unique: true
    }

    public String getRequestParameter() {
        String requestParameter = "${merchantId}|${operatingMode}|${country}" +
                "|${currency}|${amount}|${merchantOrderNo}|" +
                "${otherDetails}|${successURL}|${failureURL}|" +
                "${collaborator}"
        return requestParameter
    }

    public String getEncryptedRequestParameter() {
        return DirecPayUtility.encrypt(getRequestParameter());
    }

    public String getBillingDetail() {
        String billingDetail = "${customerName}|${customerAddress}|${customerCity}|" +
                "${customerState}|${customerPinCode}|${customerCountry}|" +
                "${customerPhoneNo1}|${customerPhoneNo2}|${customerPhoneNo3}|" +
                "${customerMobileNo}|${customerEmailId}|${otherNotes}"
        return billingDetail
    }

    public String getEncryptedBillingDetail() {
        return DirecPayUtility.encrypt(getBillingDetail())
    }

    public String getShippingDetail() {
        String shippingDetail = "${deliveryName}|${deliveryAddress }|${deliveryCity }|" +
                "${deliveryState }|${deliveryPinCode}|${deliveryCountry}|" +
                "${deliveryPhNo1 }|${deliveryPhNo2 }|${deliveryPhNo3}|" +
                "${deliveryMobileNo}"
        return shippingDetail
    }

    public String getEncryptedShippingDetail() {
        return DirecPayUtility.encrypt(getShippingDetail())
    }

    public String getEncryptedStoreCardDetail() {
        String details = "${merchantId}|${customerId}|${customerEmailId}"
        return DirecPayUtility.encrypt(details)
    }

}
