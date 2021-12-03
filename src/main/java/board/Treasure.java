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
    private boolean isClaimed = false;
    private HeldTreasureCard card;

    public Treasure(int id, BufferedImage color, BufferedImage gray){
        this.id = id;
        name = names.get(id);
        this.color = color;
        this.gray = gray;
    }

    public Treasure(String name, BufferedImage color, BufferedImage gray){
        this.name = name;
        id = names.indexOf(name);
        this.color = color;
        this.gray = gray;
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
}
