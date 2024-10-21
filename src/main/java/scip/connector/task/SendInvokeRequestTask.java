package scip.connector.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import scip.connector.model.*;
import scip.connector.model.request.InvokeRequest;
import scip.connector.model.request.ScipRequest;

@Log4j2
public class SendInvokeRequestTask extends ScipSendTask {
    @Override
    protected ScipRequest generateRequestMessage(DelegateExecution delegateExecution) {
        final InvokeRequest result = new InvokeRequest();
        result.setSignature(ScipSendTask.parseMemberSignature(delegateExecution.getVariable(PropertyNames.SIGNATURE.getName()).toString()));
        result.setOutputParams(ScipSendTask.parseParameters(delegateExecution.getVariable(PropertyNames.OUTPUTS.getName()).toString()));
        result.setInputArguments(ScipSendTask.parseArguments(delegateExecution.getVariable(PropertyNames.INPUT_ARGS.getName()).toString()));
        result.setSideEffects(Boolean.parseBoolean(delegateExecution.getVariable(PropertyNames.IS_STATEFUL.getName()).toString()));
        // todo nonce
        // todo digital signature

        return result;
    }

    @Override
    protected String getMethodName() {
        return "Invoke";
    }
}
