package board;

import card.HeldTreasureCard;
import card.TreasureCard;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Treasure implements Comparable<Treasure> {

    public static final List<Treasure> treasures = new ArrayList<>();
    public static final List<String> names = Arrays.asList("Crystal of Fire", "Ocean's Chalice", "Statue of the Wind", "Earth Stone");
    private int id;
    private String name;
    private BufferedImage color;
    private BufferedImage gray;
    private BufferedImage icon;
    private boolean isClaimed = false;
    private HeldTreasureCard card;
    private List<TreasureTile> tiles;

    public Treasure(int id, BufferedImage color, BufferedImage gray, BufferedImage icon){
        this.id = id;
        name = names.get(id);
        this.color = color;
        this.gray = gray;
        this.icon = icon;
        tiles = new ArrayList<>();
    }

    public Treasure(String name, BufferedImage color, BufferedImage gray, BufferedImage icon){
        this.name = name;
        id = names.indexOf(name);
        this.color = color;
        this.gray = gray;
        this.icon = icon;
        tiles = new ArrayList<>();
    }

    public BufferedImage getGray() {
        return gray;
    }

    public BufferedImage getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void claim(){
        isClaimed = true;
    }

    public boolean isClaimed(){
        return isClaimed;
    }

    @Override
    public int compareTo(Treasure o) {
        return id - o.id;
    }

    public boolean equals(Object o){
        if (o instanceof Treasure t){
            return t.id == this.id;
        } else {
            return false;
        }
    }

    public Integer getId() {
        return id;
    }

    public static int nameToID(String s){
        return names.indexOf(s);
    }

    public void setCard(HeldTreasureCard tc){
        this.card = tc;
    }

    public HeldTreasureCard getCard(){
        return card;
    }

    public BufferedImage getIcon(){
        return icon;
    }

    public void addTreasureTile(TreasureTile ti){
        tiles.add(ti);
    }

    public List<TreasureTile> getTreasureTiles(){
        return tiles;
    }
}
