package ScipConnector;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JsonStringCreator {
     // if present append to callback server adress //TODO: test

    public JsonStringCreator(IDCreator idCreator) {
        this.idCreator = idCreator;
    }

    private Integer port;
    private IDCreator idCreator;

    public String fromRequestParameters(Map<String, Object> requestParameters) {
        HashMap<String, Object> JsonRequest = new HashMap();
        HashMap<String, Object> params = new HashMap();


        JsonRequest.put("jsonrpc", 2.0);
        JsonRequest.put("method", "Invoke");
        JsonRequest.put("id", idCreator.newID());

        params.put("functionIdentifier",requestParameters.get("functionIdentifier"));
        params.put("callbackUrl",requestParameters.get("callbackUrl"));
        if(this.port != null){
            params.put("callbackUrl",requestParameters.get("callbackUrl")+":"+Integer.toString(this.port));//TODO: some real parsing and doing this correctly
        }
        if(requestParameters.containsKey("doc")) {
            params.put("doc", Double.parseDouble((String) requestParameters.get("doc"))); //TODO: check for null
        }
        if(requestParameters.containsKey("timeout")) {
            params.put("timeout", Double.parseDouble((String) requestParameters.get("timeout")));
        }
        params.put("correlationIdentifier",requestParameters.get("correlationIdentifier"));

        params.put("inputs", getParameters(requestParameters, "inputs"));
        params.put("outputs", getParameters(requestParameters, "outputs"));//jsonOutputs);
        params.put("signature", ""); //TODO: correct signature
        JsonRequest.put("params", params);



        String jsonString = new Gson().toJson(JsonRequest);
        return jsonString;
    }

    private ArrayList getParameters(Map<String, Object> requestParameters, String key) {
        // return an array list of JsonObjects for the output and input parameters

        ArrayList outputs = (ArrayList) requestParameters.get(key);
        ArrayList jsonOutputs = new ArrayList();
        for (Object input : outputs) {
            JsonObject jsonOutput = JsonParser.parseString(input.toString()).getAsJsonObject();
            jsonOutputs.add(jsonOutput);
        }
        return jsonOutputs;
    }

    public JsonStringCreator setPort(Integer port) {
        this.port = port;
        return this;
    }
}
