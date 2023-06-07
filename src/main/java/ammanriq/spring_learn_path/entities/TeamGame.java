package ammanriq.spring_learn_path.entities;

import java.util.UUID;

public class TeamGame {
    UUID id;
    UUID teamId;
    UUID gameId;

    public TeamGame(UUID teamId, UUID gameId) {
        this.id = UUID.randomUUID();
        this.teamId = teamId;
        this.gameId = gameId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
