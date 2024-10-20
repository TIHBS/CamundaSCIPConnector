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
import scip.connector.model.jspnrpc.JsonRpcRequest;
import scip.connector.model.jspnrpc.JsonRpcResponse;
import scip.connector.model.request.MemberSignature;
import scip.connector.model.request.Parameter;
import scip.connector.model.request.ScipRequest;
import scip.connector.model.response.ScipResponse;

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
        final String scl = generateScl(delegateExecution);
        final JsonRpcRequest request = generateJsonRpcRequest(delegateExecution);
        final ScipInvoker invoker = new ScipInvoker(scl);
        final JsonRpcResponse response = invoker.sendRequest(request);

        if (response.getError() == null) {
            this.handleResponse(response.getResult(), delegateExecution);
        } else {
            throw new BpmnError(String.valueOf(response.getError().getCode()), response.getError().getMessage());
        }
    }

    final protected JsonRpcRequest generateJsonRpcRequest(DelegateExecution delegateExecution) {
        final String method = getMethodName();
        final ScipRequest request = generateRequestMessage(delegateExecution);
        request.setCorrelationId(delegateExecution.getProcessInstanceId() + "_" + request.getCorrelationId());
        final String id = IDProvider.newID();

        return JsonRpcRequest.builder().method(method).params(request).id(id).build();
    }

    final protected String generateScl(DelegateExecution delegateExecution) {
        final String gateway = delegateExecution.getVariable("gatewayUrl").toString();
        final String query = getSclQueryParams(delegateExecution);
        final String scl = String.format("%s?%s", gateway, query);
        log.info("scl='{}'", scl);
        return scl;
    }

    protected abstract ScipRequest generateRequestMessage(DelegateExecution delegateExecution);

    protected abstract String getSclQueryParams(DelegateExecution delegateExecution);

    protected abstract String getMethodName();

    protected abstract void handleResponse(String response, DelegateExecution delegateExecution);
}
