package ammanriq.spring_learn_path.application.services;

import ammanriq.spring_learn_path.application.dtos.CreatePlayerCommand;
import ammanriq.spring_learn_path.application.exceptions.PlayerNotFoundException;
import ammanriq.spring_learn_path.data.entities.Player;
import ammanriq.spring_learn_path.data.entities.Team;
import ammanriq.spring_learn_path.data.repositories.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerServiceImp implements PlayerService {

    private final PlayerRepository playerRepository;

    private final TeamService teamService;

    public PlayerServiceImp(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public List<Player> getPlayers() {
        return playerRepository.getAll();
    }

    @Override
    public Optional<Player> getPlayerById(UUID id) {
        return playerRepository.getById(id);
    }

    @Override
    public void createPlayer(CreatePlayerCommand playerDto) throws Exception {
        if (playerDto.name() == null ||
                playerDto.name().isBlank() ||
                playerDto.number() < 0 ||
                playerDto.position() == null ||
                playerDto.teamId() == null) {
            throw new IllegalArgumentException();
        }

        Team team = teamService.getTeamById(playerDto.teamId());

        if (team == null) {
            throw new IllegalArgumentException();
        }

        Player player = new Player(
                playerDto.name(),
                playerDto.number(),
                playerDto.position(),
                team);

        playerRepository.save(player);
    }

    @Override
    public void deletePlayer(UUID id) throws PlayerNotFoundException {
        if (id == null) {
            throw new PlayerNotFoundException(id);
        }

        playerRepository.deleteById(id);
    }

}
