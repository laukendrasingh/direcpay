<html>

<head>
    <title>DirecPay Failure Response</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body style="background-color: hsl(29, 80%, 87%)">

<h1>DirecPay Failure Response</h1>

<table border="1" style="width:500px;border-spacing: 0">
    <tr>
        <td>
            <strong>DirecPayReferenceId</strong>
        </td>
        <td>
            ${responseCommand.direcPayReferenceId}
        </td>
    </tr>
    <tr>
        <td>
            <strong>DirecPayTransactionStatus</strong>
        </td>
        <td>
            ${responseCommand.transactionStatus}
        </td>
    </tr>
    <tr>
        <td>
            <strong>Country</strong>
        </td>
        <td>
            ${responseCommand.country}
        </td>
    </tr>
    <tr>
        <td>
            <strong>Currency</strong>
        </td>
        <td>
            ${responseCommand.currency}
        </td>
    </tr>
    <tr>
        <td>
            <strong>OtherDetails</strong>
        </td>
        <td>
            ${responseCommand.otherDetails}
        </td>
    </tr>
    <tr>
        <td>
            <strong>MerchantOrderNo</strong>
        </td>
        <td>
            ${responseCommand.merchantOrderNo}
        </td>
    </tr>
    <tr>
        <td>
            <strong>PostingAmount</strong>
        </td>
        <td>
            ${responseCommand.postingAmount}
        </td>
    </tr>
</table>
</body>
</html>
