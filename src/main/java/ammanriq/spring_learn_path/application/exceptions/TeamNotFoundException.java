package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(UUID teamId) {
        super("Could not find Team by id '" + teamId.toString() + "'");
    }
}
