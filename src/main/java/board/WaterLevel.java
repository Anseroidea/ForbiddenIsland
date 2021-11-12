package board;

public class WaterLevel {

    public static final int NOVICE = 0;
    public static final int NORMAL = 1;
    public static final int ELITE = 2;
    public static final int LEGENDARY = 3;

    public WaterLevel() throws InvalidDifficultyException {
        this(NORMAL);
    }
    public WaterLevel(int d) throws InvalidDifficultyException {
        if (d < 0 || d > 3) {
            throw new InvalidDifficultyException(d);
        } else {

        }
    }

}
