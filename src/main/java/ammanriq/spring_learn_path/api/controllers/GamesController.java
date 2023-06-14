package ammanriq.spring_learn_path.api.controllers;

import ammanriq.spring_learn_path.application.dtos.CreateGameCommand;
import ammanriq.spring_learn_path.application.services.GameService;
import ammanriq.spring_learn_path.data.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "games")
public class GamesController {
    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Void> createGame(@RequestBody CreateGameCommand request) {
        try {
            gameService.createGame(request);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<Game> getGames() {
        try {
            gameService.getGames();

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable UUID id) {
        try {
            gameService.getGameById(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Game> getGamesByTeamId(@PathVariable UUID id) {
        try {
            gameService.getGamesByTeamId(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID id) {
        try {
            gameService.deleteGame(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startGame(@PathVariable UUID id) {
        try {
            gameService.startGame(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping("/{id}/home-goal")
    public ResponseEntity<Void> scoreHomeTeam(@PathVariable UUID id) {
        try {
            gameService.scoreHomeTeam(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping("/{id}/away-goal")
    public ResponseEntity<Void> scoreAwayTeam(@PathVariable UUID id) {
        try {
            gameService.scoreAwayTeam(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<String> getScoreGame(@PathVariable UUID id) {
        try {
            gameService.getScoreGame(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<Void> endGame(@PathVariable UUID id) {
        try {
            gameService.endGame(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }

    }

    @GetMapping("/{id}/end")
    public ResponseEntity<String> resultsHistory(@PathVariable UUID id) {
        try {
            gameService.resultsHistory(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

}