package scip.connector.model.jspnrpc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import scip.connector.model.request.ScipRequest;

@Setter
@Getter
@Builder
@ToString
public class JsonRpcRequest {
    private final String jsonrpc = "2.0";
    private String id;
    private String method;
    private ScipRequest params;
}
