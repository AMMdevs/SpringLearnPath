package ammanriq.spring_learn_path.data;

import ammanriq.spring_learn_path.data.entities.Game;
import ammanriq.spring_learn_path.data.entities.Player;
import ammanriq.spring_learn_path.data.entities.Team;
import ammanriq.spring_learn_path.data.entities.TeamGame;
import ammanriq.spring_learn_path.data.enums.Position;

import java.util.HashMap;
import java.util.UUID;

public class ApplicationDatabase {
    public final HashMap<UUID, Game> gamesTable = new HashMap<>();
    public final HashMap<UUID, Team> teamsTable = new HashMap<>();
    public final HashMap<UUID, Player> playersTable = new HashMap<>();
    public final HashMap<UUID, TeamGame> teamGamesTable = new HashMap<>();

    public ApplicationDatabase() {
        Team valenciaCf = new Team("Valencia CF", "Valencia");
        teamsTable.put(valenciaCf.getId(), valenciaCf);
        Team madridFc = new Team("Real Madrid FC", "Madrid");
        teamsTable.put(madridFc.getId(), madridFc);

        Player joseG = new Player("José Gayà", 14, Position.CENTRE_BACK, valenciaCf);
        playersTable.put(joseG.getId(), joseG);

        Player karimB = new Player("Karim Benzema", 9, Position.CENTRE_FORWARD, madridFc);
        playersTable.put(joseG.getId(), joseG);

        Game game1 = new Game(valenciaCf, madridFc);
        gamesTable.put(game1.getId(), game1);

        Game game2 = new Game(madridFc, valenciaCf);
        gamesTable.put(game2.getId(), game2);
    }

}
