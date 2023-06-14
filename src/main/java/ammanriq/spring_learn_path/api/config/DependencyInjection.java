package ammanriq.spring_learn_path.api.config;

import ammanriq.spring_learn_path.application.services.*;
import ammanriq.spring_learn_path.data.ApplicationDatabase;
import ammanriq.spring_learn_path.data.repositories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyInjection {

    @Bean
    public ApplicationDatabase applicationDatabase() {
        return new ApplicationDatabase();
    }

    @Bean
    public PlayerRepository playerRepository(ApplicationDatabase database) {
        return new PlayerInMemoryRepository(database);
    }

    @Bean
    public TeamRepository teamRepository(ApplicationDatabase database) {
        return new TeamInMemoryRepository(database);
    }

    @Bean
    public GameRepository gameRepository(ApplicationDatabase database) {
        return new GameInMemoryRepository(database);
    }

    @Bean
    public PlayerService playerService(PlayerRepository playerRepository, TeamService teamService) {
        return new PlayerServiceImp(playerRepository, teamService);
    }

    @Bean
    public TeamService teamService(TeamRepository teamRepository) {
        return new TeamServiceImp(teamRepository);
    }

    @Bean
    public GameService gameService(GameRepository gameRepository, TeamService teamService, TeamRepository teamRepository) {
        return new GameServiceImp(gameRepository, teamService, teamRepository);
    }
}

