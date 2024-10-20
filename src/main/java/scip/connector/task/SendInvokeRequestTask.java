package scip.connector.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import scip.connector.model.*;
import scip.connector.model.request.InvokeRequest;
import scip.connector.model.request.ScipRequest;
import scip.connector.model.response.ScipResponse;

@Log4j2
public class SendInvokeRequestTask extends ScipSendTask {
    @Override
    protected ScipRequest generateRequestMessage(DelegateExecution delegateExecution) {
        final InvokeRequest result = new InvokeRequest();
        result.setSideEffects(Boolean.parseBoolean(delegateExecution.getVariable(PropertyNames.IS_STATEFUL.getName()).toString()));
        result.setCorrelationId(delegateExecution.getVariable(PropertyNames.CORRELATION_ID.getName()).toString());
        result.setDegreeOfConfidence(Long.parseLong(delegateExecution.getVariable(PropertyNames.DEGREE_OF_CONFIDENCE.getName()).toString()));
        result.setSignature(ScipSendTask.parseMemberSignature(delegateExecution.getVariable(PropertyNames.SIGNATURE.getName()).toString()));
        result.setOutputParams(ScipSendTask.parseParameters(delegateExecution.getVariable(PropertyNames.OUTPUTS.getName()).toString()));
        result.setInputArguments(ScipSendTask.parseArguments(delegateExecution.getVariable(PropertyNames.INPUT_ARGS.getName()).toString()));
        result.setCallbackUrl("http://localhost:9090/submit-transaction/dummy");
        result.setTimeout(100000L);
        log.info("Generated InvokeRequest: {}", result);

        return result;
    }

    @Override
    protected String getSclQueryParams(DelegateExecution delegateExecution) {
        return delegateExecution.getVariable(PropertyNames.SMART_CONTRACT.getName()).toString();
    }

    @Override
    protected String getMethodName() {
        return "Invoke";
    }

    @Override
    protected void handleResponse(String response, DelegateExecution delegateExecution) {
        log.info("Received response: {}", response);
    }
}
