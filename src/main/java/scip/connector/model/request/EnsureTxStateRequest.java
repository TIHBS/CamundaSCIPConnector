package scip.connector.model.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnsureTxStateRequest extends ScipRequest {
    private String ref;
}
