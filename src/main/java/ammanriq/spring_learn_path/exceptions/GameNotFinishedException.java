package ammanriq.spring_learn_path.exceptions;

import java.util.UUID;

public class GameNotFinishedException extends Exception {

    public GameNotFinishedException(UUID gameId) {
        super("The game '" + gameId.toString() + "' is not finished.");
    }
}
