package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class GameNotScheduledException extends Exception {

    public GameNotScheduledException(UUID gameId) {
        super("The game '" + gameId.toString() + "' is not scheduled.");
    }
}
