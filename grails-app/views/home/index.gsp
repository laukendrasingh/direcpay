<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Make Payment</title>
</head>

<body>

<h1>Make Payment Form</h1>

<form id="makePaymentForm" name="makePaymentForm" action="http://direcpay.qa3.intelligrape.net/direcPay" method="post">
    <p>Please put all fields here</p>
    <input type="text" name="country" value="IND"/>
    <input type="submit" name="submit" value="Submit"/>
</form>

<h1>Payment Details Form</h1>Not working

<form id="paymentDetailsForm" name="paymentDetailsForm" action="http://direcpay.qa3.intelligrape.net/direcPay/pullPaymentDetails"
      method="post">
    <input type="submit" name="submit" value="Submit"/>
</form>

</body>
</html>