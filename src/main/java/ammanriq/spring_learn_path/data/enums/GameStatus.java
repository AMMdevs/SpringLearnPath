package ammanriq.spring_learn_path.data.enums;

public enum GameStatus {
    SCHEDULED("Scheduled"),
    STARTED("Started"),
    FINISHED("Finished");

    private final String displayName;

    GameStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
