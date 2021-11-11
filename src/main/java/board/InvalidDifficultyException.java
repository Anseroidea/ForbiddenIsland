package board;

public class InvalidDifficultyException extends Exception {
    public InvalidDifficultyException(int d){
        super("Unknown Difficulty ID: " + d);
    }
}
