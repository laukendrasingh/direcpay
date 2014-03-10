<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Make Payment</title>
</head>

<body>

<h1>Make Payment...</h1>

<form id="makePaymentForm" name="makePaymentForm" action="http://direcpay.qa3.intelligrape.net/direcPay" method="post">
    <p>All details are hard coded</p>
    %{--<input type="text" name="country" value="IND"/>--}%
    <input type="submit" name="make payment" value="Submit"/>
</form>

<h1>Payment Details For ReferenceId: 1001403000365347</h1>

<form id="paymentDetailsForm" name="paymentDetailsForm" action="http://direcpay.qa3.intelligrape.net/direcPay/pullPaymentDetails"
      method="post">
    <input type="submit" name="Payment Detail" value="Submit"/>
</form>

</body>
</html>