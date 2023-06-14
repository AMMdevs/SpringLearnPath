package ammanriq.spring_learn_path.api.controllers;

import ammanriq.spring_learn_path.application.dtos.CreatePlayerCommand;
import ammanriq.spring_learn_path.application.services.PlayerService;
import ammanriq.spring_learn_path.data.entities.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "players")
public class PlayersController {
    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<Player> getPlayers() {
        try {
            playerService.getPlayers();

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
    public ResponseEntity<Player> getPlayerById(@PathVariable UUID id) {
        try {
            playerService.getPlayerById(id);

            return ResponseEntity
                    .ok()
                    .build();

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerCommand request) {
        try {
            playerService.createPlayer(request);

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
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID id) {
        try {
            playerService.deletePlayer(id);

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
