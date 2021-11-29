package board;

public class InvalidDifficultyException extends Exception {
    public InvalidDifficultyException(int d){
        super("Unknown Difficulty - level: " + d/10 + ", steps: " + d % 10);
    }
}
