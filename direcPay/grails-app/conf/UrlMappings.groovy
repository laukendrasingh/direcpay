class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'direcPay', action: 'index')
        "/pullPaymentDetails"(controller: 'direcPay', action: 'pullPaymentDetails')
        "/returnPaymentDetails"(controller: 'direcPay', action: 'returnPaymentDetails')

        "500"(view: '/error')
    }
}
