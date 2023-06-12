package ammanriq.spring_learn_path.application.dtos;

import java.util.UUID;

public record CreateGameCommand(
        UUID homeTeamId,
        UUID awayTeamId) {
}
