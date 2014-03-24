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
        println("paymentSuccess.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand command = new  PaymentResponseCommand(params.responseparams)
        direcPayService.save(command)
        render(view: 'paymentSuccess', model: [responseCommand: responseCommand])
    }

    def failure() {
        println("paymentFailure.....,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand responseCommand = new PaymentResponseCommand(params.responseparams)
        direcPayService.save(responseCommand)
        render(view: 'paymentFailure', model: [responseCommand: responseCommand])
    }
}
