package scip.connector.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class MemberSignature {
    private String name;
    private boolean function;
    private List<Parameter> parameters;
}
