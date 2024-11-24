package scip.connector.task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import scip.connector.IDProvider;
import scip.connector.ScipInvoker;
import scip.connector.model.Argument;
import scip.connector.model.PropertyNames;
import scip.connector.model.jspnrpc.JsonRpcRequest;
import scip.connector.model.jspnrpc.JsonRpcResponse;
import scip.connector.model.request.MemberSignature;
import scip.connector.model.request.Parameter;
import scip.connector.model.request.ScipRequest;

import java.lang.reflect.Type;
import java.util.List;

@Log4j2
public abstract class ScipSendTask implements JavaDelegate {

    protected static List<Argument> parseArguments(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Argument>>() {
        }.getType();

        return gson.fromJson(jsonString, listType);
    }

    protected static MemberSignature parseMemberSignature(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, MemberSignature.class);
    }

    protected static List<Parameter> parseParameters(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Parameter>>() {
        }.getType();

        return gson.fromJson(jsonString, listType);
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String scl = delegateExecution.getVariable(PropertyNames.SCL.getName()).toString();
        final JsonRpcRequest request = generateJsonRpcRequest(delegateExecution);
        final ScipInvoker invoker = new ScipInvoker(scl);
        log.info("\n\n****** {} *******:\nSending the following request to {}: {}\n", getBlockmeTaskName(), scl, request);
        final JsonRpcResponse response = invoker.sendRequest(request);

        if (response.getError() == null) {
            this.handleResponse(response.getResult());
        } else {
            log.info("\n\n****** {} ******:\nReceived synchronous SCIP error: {}\n", getBlockmeTaskName(), response);
            throw new BpmnError(String.valueOf(response.getError().getCode()), response.getError().getMessage());
        }
    }

    final protected void addGeneralRequestMessageEntries(ScipRequest scipRequest, DelegateExecution delegateExecution) {
        scipRequest.setCorrelationId(delegateExecution.getProcessBusinessKey() + "_" + delegateExecution.getVariable(PropertyNames.CORRELATION_ID.getName()).toString());
        scipRequest.setDegreeOfConfidence(Long.parseLong(delegateExecution.getVariable(PropertyNames.DEGREE_OF_CONFIDENCE.getName()).toString()));
        scipRequest.setCallbackUrl("http://localhost:8080/engine-rest/message");
        scipRequest.setTimeout(100000L);
    }

    final protected JsonRpcRequest generateJsonRpcRequest(DelegateExecution delegateExecution) {
        final String method = getMethodName();
        final ScipRequest request = generateRequestMessage(delegateExecution);
        this.addGeneralRequestMessageEntries(request, delegateExecution);
        log.debug("Generated {}Request: {}", getMethodName(), request);
        final String id = IDProvider.newID();

        return JsonRpcRequest.builder().method(method).params(request).id(id).build();
    }

    protected void handleResponse(String response) {
        log.info("\n\n****** {} ******:\nReceived synchronous SCIP response: {}\n", getBlockmeTaskName(), response);
    }

    protected abstract ScipRequest generateRequestMessage(DelegateExecution delegateExecution);

    protected abstract String getMethodName();

    protected abstract String getBlockmeTaskName();
}
