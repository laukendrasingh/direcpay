package direcPay

import com.intelligrape.direcPay.DirecPayService
import com.intelligrape.direcPay.command.PaymentResponseCommand

class HomeController {

    DirecPayService direcPayService

    def index() {
        render(view: 'index')
    }

    def returnTransactionDetail() {
        println "returnTransactionDetail..................\n response: ${response.dump()}, params: ${params.dump()}"
        render("success returnTransactionDetail")
        return
    }

    def success() {
        log.debug("paymentSuccess.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand responseCommand = direcPayService.save(params)
        render(view: 'paymentSuccess', model: [responseCommand: responseCommand])
    }

    def failure() {
        log.debug("paymentFailure.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand responseCommand = PaymentResponseCommand.populate(params.responseparams)
        render(view: 'paymentFailure', model: [responseCommand: responseCommand])
    }
}
