package scip.connector.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import scip.connector.model.PropertyNames;
import scip.connector.model.request.ScipRequest;
import scip.connector.model.request.SendTxRequest;

@Log4j2
public class SendSendTxRequestTask extends ScipSendTask {
    @Override
    protected ScipRequest generateRequestMessage(DelegateExecution delegateExecution) {
        final SendTxRequest result = new SendTxRequest();
        result.setValue(Long.valueOf(delegateExecution.getVariable(PropertyNames.VALUE.getName()).toString()));
        // todo nonce
        // todo digital signature

        return result;
    }

    @Override
    protected String getMethodName() {
        return "Invoke";
    }

}
