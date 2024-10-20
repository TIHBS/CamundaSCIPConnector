package scip.connector.model.request;

import lombok.*;
import scip.connector.model.Argument;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvokeRequest extends ScipRequest{
    private MemberSignature signature;
    private List<Parameter> outputParams;
    private List<Argument> inputArguments;
    private boolean sideEffects;
    private Long nonce;
    private String digitalSignature;
}
