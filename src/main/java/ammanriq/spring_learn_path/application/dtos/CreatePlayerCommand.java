package ammanriq.spring_learn_path.application.dtos;

import ammanriq.spring_learn_path.data.enums.Position;

import java.util.UUID;

public record CreatePlayerCommand(
        String name,
        int number,
        Position position,
        UUID teamId) {
}
