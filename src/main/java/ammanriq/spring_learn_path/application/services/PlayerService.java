package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreatePlayerCommand;
import ammanriq.spring_learn_path.application.exceptions.PlayerNotFoundException;
import ammanriq.spring_learn_path.data.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PlayerService {

    List<Player> getPlayers();

    Optional<Player> getPlayerById(UUID id);

    void createPlayer(CreatePlayerCommand playerDto) throws Exception;

    void deletePlayer(UUID id) throws PlayerNotFoundException;

}
