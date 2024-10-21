package scip.connector.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import scip.connector.model.PropertyNames;
import scip.connector.model.request.ReceiveTxRequest;
import scip.connector.model.request.ScipRequest;
import scip.connector.model.request.SendTxRequest;

@Log4j2
public class SendReceiveTxRequestTask extends ScipSendTask{
    @Override
    protected ScipRequest generateRequestMessage(DelegateExecution delegateExecution) {
        final ReceiveTxRequest result = new ReceiveTxRequest();
        result.setFrom(delegateExecution.getVariable(PropertyNames.FROM.getName()).toString());

        return result;
    }

    @Override
    protected String getMethodName() {
        return "ReceiveTx";
    }

}
