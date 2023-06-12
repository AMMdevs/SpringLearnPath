package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(UUID playerId) {
        super("Could not find Player by id '" + playerId.toString() + "'");
    }
}
