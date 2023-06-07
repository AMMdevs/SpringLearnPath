package ammanriq.spring_learn_path.enums;

public enum Result {
    WIN("Win", 2),
    LOSS("Loss", 0),
    DRAW("Draw", 1);

    private final String displayName;
    private final int point;

    Result(String displayName, int point) {
        this.displayName = displayName;
        this.point = point;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPoint() {
        return point;
    }

}
