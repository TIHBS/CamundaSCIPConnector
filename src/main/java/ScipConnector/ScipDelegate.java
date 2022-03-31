package ScipConnector;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;

public class ScipDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.getVariables().forEach((key, value) -> System.out.println(key));

        Map variables = delegateExecution.getVariables();
        variables.put("correlationIdentifier",delegateExecution.getProcessInstanceId()+"_"+delegateExecution.getCurrentActivityId());
        String jsonString = new JsonStringCreator(new IDCreator()).fromRequestParameters(variables);
        System.out.println(jsonString);
        
        String url = (String) delegateExecution.getVariables().get("url");
        HashMap<String, Object> result = new ScipCaller(url).setJsonrequest(jsonString).sendRequest();

        delegateExecution.setVariables(result);

        if(result.containsKey("errorCode")){
          if((Integer) result.get("errorCode")!=0){
              throw new BpmnError(result.get("errorCode").toString());
          }
        }


    }
}
