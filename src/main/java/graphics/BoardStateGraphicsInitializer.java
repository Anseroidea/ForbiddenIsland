package graphics;

public class BoardStateGraphicsInitializer {

    private static BoardGraphics bg;
    private static PlayerGraphics pg;


    public static BoardGraphics getBg() {
        return bg;
    }

    public static PlayerGraphics getPg(){
        return pg;
    }

    public static void setBg(BoardGraphics bg) {
        BoardStateGraphicsInitializer.bg = bg;
    }

    public static void setPg(PlayerGraphics pg){
        BoardStateGraphicsInitializer.pg = pg;
    }

    public static void initialize(){
        bg.initializeTiles();
        pg.initializePlayers();
    }
}
