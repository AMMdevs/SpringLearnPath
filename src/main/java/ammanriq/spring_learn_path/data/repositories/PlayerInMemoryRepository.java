package ammanriq.spring_learn_path.data.repositories;

import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class PlayerInMemoryRepository implements PlayerRepository {

    private final ApplicationDatabase database;

    public PlayerInMemoryRepository(ApplicationDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Player player) {
        database.playersTable.put(player.getId(), player);
    }

    @Override
    public Optional<Player> getById(UUID playerId) {
        Player player = database.playersTable.get(playerId);

        return Optional.of(player);
    }

    @Override
    public List<Player> getAll() {
        return database.playersTable.values().stream().toList();
    }

    @Override
    public void deleteById(UUID playerId) {
        database.playersTable.remove(playerId);
    }
}
