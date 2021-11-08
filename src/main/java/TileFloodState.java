import javafx.scene.paint.Color;

enum TileFloodState {

    LAND(Color.GREEN),
    FLOOD(Color.TURQUOISE),
    SUNK(Color.LIGHTBLUE),
    WATER(Color.LIGHTBLUE);

    private Color color;

    TileFloodState(Color c){
        color = c;
    }

    public TileFloodState next(){
        switch (this){
            case LAND: {
                return FLOOD;
            }
            case FLOOD: {
                return SUNK;
            }
            default: {
                return LAND;
            }
        }
    }

    public Color getColor(){
        return color;
    }

    public String toString(){
        return name();
    }

}
