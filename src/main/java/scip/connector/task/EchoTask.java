package scip.connector.task;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Log4j2
public class EchoTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        final String varName = delegateExecution.getCurrentActivityId() + "_message";
        final String message = (String) delegateExecution.getVariable(varName);
        log.info("****** {}: {} ******", delegateExecution.getCurrentActivityId(), message);
    }
}
