package scip.connector.model.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class SendTxRequest extends ScipRequest {
    private Long value;
    private Long nonce;
    private String digitalSignature;
}
