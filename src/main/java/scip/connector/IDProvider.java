package scip.connector;

import java.util.UUID;

public class IDProvider {
    public static String newID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
