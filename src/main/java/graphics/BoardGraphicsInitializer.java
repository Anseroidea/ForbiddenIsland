package graphics;

public class BoardGraphicsInitializer {

    private static BoardGraphics bg;


    public static BoardGraphics getBg() {
        return bg;
    }

    public static void setBg(BoardGraphics bg) {
        BoardGraphicsInitializer.bg = bg;
    }

    public static void initializeTiles(){
        bg.initializeTiles();
    }
}
