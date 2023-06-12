package ammanriq.spring_learn_path.application.exceptions;

import java.util.UUID;

public class NullFieldsException extends Exception {

    public NullFieldsException() {
        super("These fields must be valid and cannot be empty.");
    }
}
