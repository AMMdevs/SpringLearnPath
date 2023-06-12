package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class GameFinishedException extends Exception {

    public GameFinishedException(UUID gameId) {
        super("The game '" + gameId.toString() + "' is already finished.");
    }
}
