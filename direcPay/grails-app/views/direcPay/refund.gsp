<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DirecPay Refund</title>
</head>

<body style="text-align: center">

<form id="direcPayForm" name="direcPayForm"
      action="${direcPayRefundURL}" method="post" style="display: none">

    <table>
        <tr height="100%">
            <td>
                <input type="hidden" name="requestparams" value="${requestparams}"/>
                <input type="hidden" name="merchantId" value="${merchantId}"/>
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
