package scip.connector.task;

import org.junit.jupiter.api.Test;
import scip.connector.model.Argument;
import scip.connector.model.request.MemberSignature;
import scip.connector.model.request.Parameter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScipTaskTest {

    @Test
    void parseParameters() {
        String paramsJsonString = """
                [
                  {
                    "name": "isReally",
                    "type": "{ \\"type\\": \\"boolean\\" }"
                  },
                  {
                    "name": "msg",
                    "type": "{ \\"type\\": \\"string\\" }"
                  }
                ]""";
        List<Parameter> result = ScipSendTask.parseParameters(paramsJsonString);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("isReally", result.get(0).getName());
        assertEquals("msg", result.get(1).getName());
        assertEquals("{ \"type\": \"boolean\" }", result.get(0).getType());
        assertEquals("{ \"type\": \"string\" }", result.get(1).getType());
        List<Parameter> result2 = ScipSendTask.parseParameters("[]");
        assertNotNull(result2);
        assertTrue(result2.isEmpty());
        List<Parameter> result3 = ScipSendTask.parseParameters("");
        assertNull(result3);
    }

    @Test
    void parseMemberSignature() {
        String paramsJsonString = """
                {
                    "name": "sendMoney",
                    "function": "true",
                    "parameters":
                    [
                      {
                        "name": "isReally",
                        "type": "{ \\"type\\": \\"boolean\\" }"
                      },
                      {
                        "name": "msg",
                        "type": "{ \\"type\\": \\"string\\" }"
                      }
                    ]
                }""";
        MemberSignature result = ScipSendTask.parseMemberSignature(paramsJsonString);
        assertNotNull(result);
        assertEquals("sendMoney", result.getName());
        assertTrue(result.isFunction());
        List<Parameter> parameters = result.getParameters();
        assertNotNull(parameters);
        assertEquals(2, parameters.size());
        assertEquals("isReally", parameters.get(0).getName());
        assertEquals("msg", parameters.get(1).getName());
        assertEquals("{ \"type\": \"boolean\" }", parameters.get(0).getType());
        assertEquals("{ \"type\": \"string\" }", parameters.get(1).getType());
        List<Parameter> result2 = ScipSendTask.parseParameters("[]");
        assertNotNull(result2);
        assertTrue(result2.isEmpty());
        List<Parameter> result3 = ScipSendTask.parseParameters("");
        assertNull(result3);
    }

    @Test
    void parseArguments() {
        String argsJsonString = """
                [
                  {
                    "name": "isReally",
                    "value": "true"
                  },
                  {
                    "name": "msg",
                    "value": "Happy"
                  }
                ]""";
        List<Argument> result = ScipSendTask.parseArguments(argsJsonString);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("isReally", result.get(0).getName());
        assertEquals("msg", result.get(1).getName());
        assertEquals("true", result.get(0).getValue());
        assertEquals("Happy", result.get(1).getValue());
        result = ScipSendTask.parseArguments("[]");
        assertNotNull(result);
        assertTrue(result.isEmpty());
        result = ScipSendTask.parseArguments("");
        assertNull(result);
    }
}