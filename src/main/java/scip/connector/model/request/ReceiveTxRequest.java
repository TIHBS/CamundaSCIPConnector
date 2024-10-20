package scip.connector.model.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceiveTxRequest extends ScipRequest {
    private String from;
}
