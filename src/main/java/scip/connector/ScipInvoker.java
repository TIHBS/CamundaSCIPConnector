package scip.connector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import scip.connector.model.jspnrpc.JsonRpcRequest;
import scip.connector.model.jspnrpc.JsonRpcResponse;
import scip.connector.model.response.ScipResponse;

import java.io.IOException;

@Log4j2
public class ScipInvoker {
    private final String url;

    public ScipInvoker(String url) {
        this.url = url;
    }
    //class to send a Scip request

    public JsonRpcResponse sendRequest(JsonRpcRequest request) throws IOException {
        final HttpPost post = new HttpPost(this.url);
        final StringEntity body = new StringEntity(this.convertToJson(request));
        post.setEntity(body);
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "application/json");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client
                     .execute(post)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            log.info("Received synchronous SCIP response: {}", jsonResponse);

            return (new Gson()).fromJson(jsonResponse, JsonRpcResponse.class);
        }

    }

    private String convertToJson(Object request) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(request);
    }

}
