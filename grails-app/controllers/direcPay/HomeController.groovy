package direcPay

import com.intelligrape.direcPay.DirecPayService
import com.intelligrape.direcPay.command.PaymentResponseCommand

class HomeController {

    DirecPayService direcPayService

    def index() {
        render(view: 'index')
    }

    def returnTransactionDetail() {
        println "=================ReturnTransactionDetail=================\n response: ${response.dump()}, params: ${params.dump()}"
        render("success returnTransactionDetail")
        return
    }

    def success() {
        println("=================PaymentSuccess=================,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand command = new PaymentResponseCommand(params.responseparams)
        println("command: ${command.dump()}")
        direcPayService.save(command)
        render(view: 'paymentSuccess', model: [responseCommand: command])
    }

    def failure() {
        println("=================PaymentFailure=================,\nparams: ${params.dump()},\nresponse: ${response.dump()}")
        PaymentResponseCommand responseCommand = new PaymentResponseCommand(params.responseparams)
        direcPayService.save(responseCommand)
        render(view: 'paymentFailure', model: [responseCommand: responseCommand])
    }
}
