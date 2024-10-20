package scip.connector.model.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class EnsureTxState extends ScipRequest {
    private String ref;
}
