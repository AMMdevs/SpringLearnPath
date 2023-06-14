package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {

    void save(Player player);

    Optional<Player> getById(UUID playerId);

    List<Player> getAll();

    void deleteById(UUID playerId);
}
