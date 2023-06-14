package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.entities.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    void save(Game game);

    Optional<Game> getById(UUID gameId);

    List<Game> getAll();

    List<Game> getByTeamId(UUID id);

    void deleteById(UUID gameId);
}
