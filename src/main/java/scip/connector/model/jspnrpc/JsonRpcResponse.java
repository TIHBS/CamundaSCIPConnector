package scip.connector.model.jspnrpc;

import lombok.*;
import scip.connector.model.request.ScipRequest;
import scip.connector.model.response.ScipResponse;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JsonRpcResponse {
    private final String jsonrpc = "2.0";
    private String id;
    private String result;
    private JsonRpcError error;
}
