package scip.connector.model.jspnrpc;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonRpcError {
    private long code;
    private String message;
    private String data;
}
