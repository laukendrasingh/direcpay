<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DirecPay Payment Transaction Details</title>
</head>

<body style="text-align: center">
<h1>DirecPay Payment Transaction Details</h1>

<form id="pullDirecPayPaymentDetailsForm" name="pullDirecPayPaymentDetailsForm"
      action="${direcPayPullTransactionDetailsURL}" method="post" style="display: none">

    <table>
        <tr height="100%">
            <td>
                <input type="hidden" name="requestparams" value="${requestparams}"/>
                <input type="submit" id="submit" name="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form>


<script language="JavaScript" type="text/javascript">
    window.onload = function () {
        document.getElementById("submit").click();
    }
</script>

</body>
</html>
