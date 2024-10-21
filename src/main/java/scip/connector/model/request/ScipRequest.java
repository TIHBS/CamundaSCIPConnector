package scip.connector.model.request;

import lombok.*;

@Data
public class ScipRequest {
    private String scl;
    private String correlationId;
    private String callbackUrl;
    private final String callbackBinding = "camunda";
    private Long degreeOfConfidence;
    private Long timeout;
}
