<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Make Payment</title>
</head>

<body>

<h1>Make Payment...</h1>

<form id="makePaymentForm" name="makePaymentForm" action="http://direcpay.qa3.intelligrape.net/direcPay" method="post">
    <p>All details are hard coded</p>
    <input type="text" name="country" value="IND"/>
    <input type="submit" name="submit" value="make payment"/>
</form>

<h1>Payment Details For ReferenceId: 1001403000366780</h1>

<form id="paymentDetailsForm" name="paymentDetailsForm"
      action="http://direcpay.qa3.intelligrape.net/direcPay/pullPaymentDetails">
    <input type="text" name="direcPayReferenceId" value="${1001403000366780}"/>
    <input type="submit" name="submit" value="Payment Detail"/>
</form>

<h1>Refund</h1>

<form id="refundForm" name="refundForm" action="http://direcpay.qa3.intelligrape.net/direcPay/refund">
    direcPayReferenceId:<input type="text" name="direcPayReferenceId" value="1001403000366782"/>
    refundAmount:<input type="text" name="refundAmount" value="10"/>
    merchantOrderNo:<input type="text" name="merchantOrderNo" value="1395824267612"/>
    <input type="submit" name="submit" value="Refund"/>
</form>

</body>
</html>