package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(UUID gameId) {
        super("Could not find Game by id '" + gameId.toString() + "'");
    }
}
