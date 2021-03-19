package model;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

public class Card {
    private String faceName, suit;
    private Image image;

    public Card(String faceName, String suit) {
        setFaceName(faceName);
        setSuit(suit);
        String fileName = faceName + "" + suit + ".png";
        image = new Image("slike/" + fileName);
    }
    public Card(Card card) {
        this.faceName = card.getFaceName();
        this.suit = card.getSuit();
        String fileName = faceName + "" + suit + ".png";
        image = new Image("slike/" + fileName);
    }

    public String getFaceName() {
        return faceName;
    }

    public static List<String> getValidFaceNames()
    {
        return Arrays.asList("2","3","4","5","6","7","8","9","10","J",
                "Q","K","A");
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setFaceName(String faceName) {
        List<String> validFaceNames = getValidFaceNames();

        if (validFaceNames.contains(faceName))
            this.faceName = faceName;
    }

    public String getSuit() {
        return suit;
    }


    public static List<String> getValidSuits()
    {
        return Arrays.asList("H","D","S","C");
    }

    public void setSuit(String suit) {
        List<String> validSuits = getValidSuits();

        if (validSuits.contains(suit))
            this.suit = suit;
    }

    public int getValue() {
        String faceName = this.getFaceName();
        if (faceName.equals("A")) return 11;
        if (faceName.equals("J")) return 12;
        if (faceName.equals("Q")) return 13;
        if (faceName.equals("K")) return 14;
        else return Integer.parseInt(faceName);
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;
        if (this.getFaceName().equals(card.getFaceName()) && this.getSuit().equals(card.getSuit())) return true;
        return false;
    }

    public String toString()
    {
        return String.format("%s of %s", faceName, suit);
    }
}