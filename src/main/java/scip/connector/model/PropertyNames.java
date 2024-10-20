package scip.connector.model;

import lombok.Getter;

@Getter
public enum PropertyNames {
    // Property names of the BlockME constructs
    SMART_CONTRACT("sc"),
    SIGNATURE("signature"),
    OUTPUTS("outputs"),
    INPUT_ARGS("inArgs"),
    OUTPUT_ARGS("outArgs"),
    IS_STATEFUL("isStateful"),
    CORRELATION_ID("corrId"),
    DEGREE_OF_CONFIDENCE("doc"),
    TO("to"),
    VALUE("value"),
    TIMESTAMP("timestamp"),
    FROM("from"),
    REF("ref");

    final String name;

    PropertyNames(String name) {
        this.name = name;
    }

}
