package board;

public class WaterLevel {

    public static final int NOVICE = 20;
    public static final int NORMAL = 21;
    public static final int ELITE = 30;
    public static final int LEGENDARY = 31;
    public static final int DEATH = 60;
    private int level = 2;
    private int steps = 0;

    public WaterLevel() throws InvalidDifficultyException {
        this(NORMAL);
    }
    public WaterLevel(int d) throws InvalidDifficultyException {
        level = d/10;
        steps = d % 10;
        if (level == 2 && steps > 1){
            throw new InvalidDifficultyException(d);
        } else if (level == 3 && steps > 2){
            throw new InvalidDifficultyException(d);
        } else if (level == 4 && steps > 1){
            throw new InvalidDifficultyException(d);
        } else if (level == 5 && steps > 1){
            throw new InvalidDifficultyException(d);
        } else if (level == 6 && steps > 0){
            throw new InvalidDifficultyException(d);
        } else if (level < 2 || level > 6){
            throw new InvalidDifficultyException(d);
        }
    }

    public void raiseLevel(){
        steps++;
        if (level == 2 && steps > 1){
            level++;
            steps = 0;
        } else if (level == 3 && steps > 2){
            level++;
            steps = 0;
        } else if (level == 4 && steps > 1){
            level++;
            steps = 0;
        } else if (level == 5 && steps > 1){
            level++;
            steps = 0;
        } else if (level == 6 && steps > 0){
            level++;
            steps = 0;
        }
    }

    public int getLevel(){
        return level;
    }

    public int getTotalSteps(){
        return switch (level){
            case 2, 4, 5 -> 2;
            case 3 -> 3;
            case 6 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }

    public int getSteps(){
        return steps;
    }

}
