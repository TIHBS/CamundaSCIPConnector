package scip.connector.task;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import scip.connector.model.PropertyNames;
import scip.connector.model.request.EnsureTxStateRequest;
import scip.connector.model.request.ScipRequest;

public class SendEnsureStateRequestTask extends ScipSendTask {
    @Override
    protected ScipRequest generateRequestMessage(DelegateExecution delegateExecution) {
        final EnsureTxStateRequest result = new EnsureTxStateRequest();
        result.setRef(delegateExecution.getVariable(PropertyNames.REF.getName()).toString());

        return result;
    }

    @Override
    protected String getMethodName() {
        return "EnsureState";
    }
}
