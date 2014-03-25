import com.intelligrape.direcPay.DirecPayCollection
import com.intelligrape.direcPay.command.DirecPayProgressStatus
import com.intelligrape.direcPay.command.DirecPayTransactionStatus

class BootStrap {

    def init = { servletContext ->
        println ".....inti app....."
        //TODO:test data
        String refId1 = '1001403000366696'
        String refId2 = '1001403000366698'
        String REF_ID_3 = '1001403000366699'
        String MERCHANT_ORDER_NO = '1395732668639'

        if (!DirecPayCollection.findByDirecPayReferenceId(refId1)) {
            DirecPayCollection collection1 = new DirecPayCollection(direcPayReferenceId: '1001403000366696',
                    merchantOrderNo: MERCHANT_ORDER_NO, progressStatus: DirecPayProgressStatus.PROCESSED,
                    transactionStatus: DirecPayTransactionStatus.TRANSACTION_BOOKED,
                    delayInterval: DirecPayTransactionStatus.TRANSACTION_BOOKED.pullInterval)
            collection1.save()
            println "saved direcpay collection1: ${collection1.dump()}"
        }

        if (!DirecPayCollection.findByDirecPayReferenceId(refId2)) {
            DirecPayCollection collection2 = new DirecPayCollection(direcPayReferenceId: '1001403000366698',
                    merchantOrderNo: MERCHANT_ORDER_NO, progressStatus: DirecPayProgressStatus.PROCESSED,
                    transactionStatus: DirecPayTransactionStatus.FUNDS_IN_CLEARING,
                    delayInterval: DirecPayTransactionStatus.FUNDS_IN_CLEARING.pullInterval)
            collection2.save()
            println "saved direcpay  collection1: ${collection2.dump()}"
        }

        if (!DirecPayCollection.findByDirecPayReferenceId(REF_ID_3)) {
            DirecPayCollection collection3 = new DirecPayCollection(direcPayReferenceId: '1001403000366699',
                    merchantOrderNo: MERCHANT_ORDER_NO, progressStatus: DirecPayProgressStatus.AWAITED,
                    transactionStatus: DirecPayTransactionStatus.SUCCESS)
            collection3.save()
            println "saved direcpay  collection1: ${collection3.dump()}"
        }
    }
    def destroy = {
        println ".....destroy app....."
    }
}
