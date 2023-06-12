package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class GameNotStartedException extends Exception {

    public GameNotStartedException(UUID gameId) {
        super("The game '" + gameId.toString() + "' is not started.");
    }
}
