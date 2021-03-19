package model;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class DeckOfCards {

    private static DeckOfCards instance = null;
    private ArrayList<Card> deck;
    private List<Card> tableCards;
    private List<Card> pcCards;
    private List<Card> myCards;
    private int myScore = 0;
    private int myNumOfCards;
    private int pcScore;
    private int pcNumOfCards;
    private static int bound = 52;
    private int lastTake;

    public static DeckOfCards getInstance() {
        if (instance == null) instance = new DeckOfCards();
        return instance;
    }

    private DeckOfCards()
    {
        List<String> suits = Card.getValidSuits();
        List<String> faceNames = Card.getValidFaceNames();

        deck = new ArrayList<>();

        for (String suit : suits)
        {
            for (String faceName : faceNames)
                deck.add(new Card(faceName,suit));
        }
        pcCards = new ArrayList<>();
        tableCards = new ArrayList<>();
        myCards = new ArrayList<>();

        shuffle();

        makePcCards();
        makeTableCards();
        makeMyCards();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card getCardAtPosition(int num) {
        return deck.get(num);
    }

    public void removeCardFromDeck(Card card) {
        deck.remove(card);
    }


    public void makeCards(List<Card> cards, int num_of_cards_left, boolean isHidden) {
        while (cards.size() < num_of_cards_left) {
            Random r = new Random();
            boolean flag = true;
            int random = r.nextInt(bound);
            Card randomCard = new Card(getCardAtPosition(random));
            String imagePath = isHidden ? "slike/red_back.png" : "slike/" + randomCard.getFaceName() + randomCard.getSuit() + ".png";
            Image image = new Image(imagePath);
            randomCard.setImage(image);
            for (Card card : cards) {
                if (card.equals(randomCard)) flag = false;
            }
            if (flag) {
                cards.add(randomCard);
                removeCardFromDeck(randomCard);
                bound--;
            }
        }
    }

    public void makePcCards(){
        makeCards(pcCards, 6, true);
    }

    public void makeTableCards() {
        makeCards(tableCards, 4, false);
    }

    public void makeMyCards() {
        makeCards(myCards ,6, false);
    }

    public String calculateFinalPoints() {
        if (myNumOfCards > pcNumOfCards) myScore += 3;
        else if (pcNumOfCards > myScore ) pcScore += 3;
        if (myScore > pcScore) return "Player";
        else return "PC";
    }

    public int calculateScore(Card card) {
        int cntScore = 0;
        if (card.getFaceName().equals("10") && card.getSuit().equals("D")) cntScore += 2;
        else if (card.getValue() >= 10) cntScore++;
        else if (card.getFaceName().equals("A") || card.getFaceName().equals("2") && card.getSuit().equals("C")) cntScore++;
        return cntScore;
    }

    public List<Card> getPcCards() {
        return pcCards;
    }

    public List<Card> getTableCards() {
        return tableCards;
    }

    public List<Card> getMyCards() {
        return myCards;
    }

    public int getMyScore() {
        return myScore;
    }

    public void setMyScore(int myScore) {
        this.myScore = myScore;
    }

    public int getPcScore() {
        return pcScore;
    }

    public void setPcScore(int pcScore) {
        this.pcScore = pcScore;
    }

    public int getMyNumOfCards() {
        return myNumOfCards;
    }

    public void setMyNumOfCards(int myNumOfCards) {
        this.myNumOfCards = myNumOfCards;
    }

    public int getPcNumOfCards() {
        return pcNumOfCards;
    }

    public void setPcNumOfCards(int pcNumOfCards) {
        this.pcNumOfCards = pcNumOfCards;
    }

    public static int getBound() {
        return bound;
    }

    public int getLastTake() {
        return lastTake;
    }

    public void setLastTake(int lastTake) {
        this.lastTake = lastTake;
    }
}