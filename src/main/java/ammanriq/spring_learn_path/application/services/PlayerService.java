package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreatePlayerCommand;
import ammanriq.spring_learn_path.application.dtos.CreateTeamCommand;
import ammanriq.spring_learn_path.application.exceptions.NullFieldsException;
import ammanriq.spring_learn_path.application.exceptions.PlayerNotFoundException;
import ammanriq.spring_learn_path.application.exceptions.TeamNotFoundException;
import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Player;
import ammanriq.spring_learn_path.data.entities.Team;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlayerService {
    private final ApplicationDatabase database;

    public PlayerService(ApplicationDatabase database) {
            this.database = database;
    }

    public List<Player> getPlayers() {
        return database.playersTable.values().stream().toList();
    }

    public Player getPlayerById(UUID id) {
        return database.playersTable.get(id);
    }

    public void createPlayer(CreatePlayerCommand playerDto) throws IllegalArgumentException {
        if (playerDto.name() == null ||
                playerDto.name().isBlank() ||
                playerDto.number() < 0 ||
                playerDto.position() == null ||
                playerDto.teamId() == null) {
            throw new IllegalArgumentException();
        }

        Team team = database.teamsTable.get(playerDto.teamId());

        if (team == null) {
            throw  new IllegalArgumentException();
        }

        Player player = new Player(
                playerDto.name(),
                playerDto.number(),
                playerDto.position(),
                team);

        database.playersTable.put(player.getId(), player);
    }

    public void deletePlayer(UUID id) throws PlayerNotFoundException {
        if (id == null) {
            throw new PlayerNotFoundException(id);
        }

        database.playersTable.remove(id);
    }

}
