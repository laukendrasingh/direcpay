It's a payment integration plugin with DirecPay

===================================================
----------------------Stack------------------------
===================================================
java 1.7
grails 2.3.2
aes6.0.jar

Git:
git URL: https://github.com/laukendra/direcPay
SSH Url: git@github.com:laukendra/direcPay.git

===================================================
-------------------Config Required------------------
===================================================
Note: Please put following config in your Config.groovy file

baseURL = http://localhost:8080/DirecPayTest/
grails.plugins.direcPay.return.transaction.details.URL = "${baseURL}/paymentDetails"
grails.plugins.direcPay.loadingText = "Loading..."
grails.plugins.direcPay.operatingMode = "DOM"
grails.plugins.direcPay.collaborator = "TOML"
grails.plugins.direcPay.encryption.secretKey = "qcAHa6tt8s0l5NN7UWPVAQ=="
grails.plugins.direcPay.merchantId = "200904281000001"