package ammanriq.spring_learn_path.entities;

import ammanriq.spring_learn_path.enums.GameStatus;

import java.util.Objects;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final Team home;
    private final Team away;
    //public int goal = 1;
    private int homeScore;
    private int awayScore;
    private GameStatus status;


    public Game(Team home, Team away) {
        this.id = UUID.randomUUID();
        this.home = home;
        this.away = away;
        this.homeScore = 0;
        this.awayScore = 0;
        this.status = GameStatus.SCHEDULED;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return homeScore == game.homeScore && awayScore == game.awayScore && Objects.equals(home, game.home) && Objects.equals(away, game.away);
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away, homeScore, awayScore);
    }

    @Override
    public String toString() {
        return "Game{" +
                "home=" + home +
                ", away=" + away +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                '}';
    }
}
