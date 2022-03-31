package ScipConnector;

import java.util.UUID;

public class IDCreator {
    public String newID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
