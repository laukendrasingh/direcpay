class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        //Make payment
        "/"(controller: 'direcPay', action: 'index')
        "/test"(controller: 'direcPay', action: 'test')
        //Server to server communication (pull transaction)
        "/pullPaymentDetails"(controller: 'direcPay', action: 'pullPaymentDetails')
        "/returnPaymentDetails"(controller: 'direcPay', action: 'returnPaymentDetails')
        //refund
        "/refund"(controller: 'direcPay', action: 'refund')
        "/responseRefundURL"(controller: 'direcPay', action: 'responseRefundURL')

        "500"(view: '/error')
    }
}
