package ScipConnector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class ScipCaller {
    public ScipCaller(String url) {
        this.url = url;
    }
    //class to send a Scip request

    public String getJsonrequest() {
        return jsonrequest;
    }

    public ScipCaller setJsonrequest(String jsonrequest) {
        this.jsonrequest = jsonrequest;
        return this;
    }


    public HashMap<String, Object> sendRequest() {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(this.getUrl());

        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "application/json");//TODO: utf8

        StringEntity payload = null;
        try {
            payload = new StringEntity(this.getJsonrequest());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(payload);

        //receive http response
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> results = new HashMap<>();
        if (response != null) {
            try {
                String json_string = EntityUtils.toString(response.getEntity());
                System.out.println(json_string);
                JsonObject reply = JsonParser.parseString(json_string).getAsJsonObject();

                if (reply.has("error")) {
                    results.put("errorCode", reply.get("error").getAsJsonObject().get("code").getAsInt());
                } else {
                    results.put("errorCode", 0);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    private String jsonrequest;

    public String getUrl() {
        return url;
    }

    public ScipCaller setUrl(String url) {
        this.url = url;
        return this;
    }

    private String url;



}
