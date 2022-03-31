package ScipConnector;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonStringCreatorTest {

    private IDCreator idCreator;

    @BeforeEach
    void setUp() {
        this.idCreator = Mockito.mock(IDCreator.class);//new IDCreator();
        Mockito.when(idCreator.newID()).thenReturn("Mocked-ID");
    }

    @Test
    void fromRequestParameters() {
        Gson gson = new Gson();

        JsonElement o1 = JsonParser.parseString("{a : {a : 2}, b : 2}");
        JsonElement o2 = JsonParser.parseString("{b : 2, a : {a : 2}}");
        assertEquals(o1, o2);

        HashMap<String, Object> map;
        //example json generated from debugger
        HashMap<String, Object> test = gson.fromJson("{\"outputs\":[\"{ \\\"name\\\": \\\"newName\\\", \\\"type\\\": \\\" {\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\"}\\\",  \\\"value\\\": \\\"foo\\\" }\"],\"payload\":\"{\\n   \\\"jsonrpc\\\": 2.0,\\n   \\\"method\\\": \\\"Invoke\\\",\\n   \\\"id\\\": 3,\\n   \\\"params\\\": {\\n       \\\"functionIdentifier\\\": \\\"getMessage\\\",\\n       \\\"outputs\\\": [{\\n         \\\"name\\\": \\\"newName\\\",\\n         \\\"type\\\": \\\" {\\\\\\\"type\\\\\\\":\\\\\\\"string\\\\\\\"}\\\",\\n         \\\"value\\\": \\\"somesasng\\\"\\n       } ],\\n       \\\"inputs\\\": [],\\n       \\\"callbackUrl\\\": \\\"http://172.16.238.1:5000\\\",\\n       \\\"doc\\\": 50,\\n       \\\"timeout\\\": 1,\\n       \\\"correlationIdentifier\\\": \\\"d8ce8110-7858-11ec-ad09-1c1b0d446449\\\",\\n       \\\"signature\\\": \\\"\\\"\\n    }\\n}\",\"inputs\":[],\"correlationIdentifier\":\"d8ce8110-7858-11ec-ad09-1c1b0d446449\",\"doc\":\"50\",\"callbackUrl\":\"http://172.16.238.1:5000\",\"functionIdentifier\":\"getMessage\",\"url\":\"http://localhost:8081/blockchain-access-layer/webapi?blockchain\\u003dethereum\\u0026blockchain-id\\u003deth-0\\u0026address\\u003d0x8d8516158141E97669B2B00b9897DD683BB44F07\"}", HashMap.class);
        String jsonString = new JsonStringCreator(idCreator).fromRequestParameters(test);

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
        JsonElement expected = JsonParser.parseString(shouldString);
        JsonElement actual = JsonParser.parseString(jsonString);

        assertEquals(expected,actual);

    }
}