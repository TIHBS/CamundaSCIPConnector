package ScipConnector;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ScipCallerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void sendRequest() {
        MockWebServer server = new MockWebServer();
        MockResponse response = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{\"id\":4,\"error\":{\"code\":-32000,\"message\":\"The specified smart contract is not found.\"},\"jsonrpc\":\"2.0\"}");
        server.enqueue(response);

        HttpUrl url = server.url("/");


        String shouldString = " {\n" +
                "           \"jsonrpc\": 2.0,\n" +
                "           \"method\": \"Invoke\",\n" +
                "           \"id\": \"Mocked-ID\",\n" +
                "           \"params\": {\n" +
                "               \"functionIdentifier\": \"getMessage\",\n" +
                "               \"outputs\": [{\n" +
                "                 \"name\": \"newName\",\n" +
                "                 \"type\": \" {\\\"type\\\":\\\"string\\\"}\",\n" +
                "                 \"value\": \"foo\"\n" +
                "               } ],\n" +
                "               \"inputs\": [],\n" +
                "               \"callbackUrl\": \"http://172.16.238.1:5000\",\n" +
                "               \"doc\": 50.0,\n" +
                //"               \"timeout\": 1,\n" +
                "               \"correlationIdentifier\": \"d8ce8110-7858-11ec-ad09-1c1b0d446449\",\n" +
                "               \"signature\":\"\""+
                "            }\n" +
                "                }";

        HashMap<String, Object> result = new ScipCaller(url.toString()).setJsonrequest(shouldString).sendRequest();

        assertEquals(result.get("errorCode"),-32000);

        RecordedRequest request = null;
        try {
            request = server.takeRequest();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("application/json", request.getHeader("Content-Type"));
        assertEquals("application/json", request.getHeader("Accept"));
        assertEquals(shouldString, request.getBody().readUtf8());
    }

}