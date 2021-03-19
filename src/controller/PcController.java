package controller;


import javafx.scene.image.Image;
import model.Card;
import model.DeckOfCards;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PcController {

    private static PcController instance = null;
    private DeckOfCards deck = DeckOfCards.getInstance();
    private SumValidator sumValidator = new SumValidator();

    public static PcController getInstance() {
        if (instance == null) instance = new PcController();
        return instance;
    }

    public void play() {
        List<Card> tableCards = deck.getTableCards();
        List<Card> pcCards = deck.getPcCards();

        int maxScore = 0;
        List<HashSet<Card>> lista = combinations(tableCards);

        HashSet<Card> cardChoice = null;
        Card selectedCard = null;
        for (Card card : pcCards) {
            for (HashSet<Card> l : lista) {
                if (sumValidator.isValidSum(card.getValue(), l)) {
                    int score = countScore(l, card);
                    if (score >= maxScore) {
                        maxScore = score;
                        cardChoice = l;
                        selectedCard = card;
                    }
                }
            }
        }

        if (cardChoice != null) {
            for (Card card : cardChoice)  {
                deck.getTableCards().remove(card);
            }
            deck.getPcCards().remove(selectedCard);
            deck.setPcScore(deck.getPcScore() + maxScore);
            deck.setPcNumOfCards(deck.getPcNumOfCards() + cardChoice.size() + 1);
            deck.setLastTake(1);
        }
        else {
            // Throwing the smallest card
            int min = 15;
            Card cardToThrow = null;
            for (Card card : pcCards) {
                if (card.getValue() < min) {
                    min = card.getValue();
                    cardToThrow = card;
                }
            }

            String name ="slike/" + cardToThrow.getFaceName() + cardToThrow.getSuit() + ".png";
            Image image = new Image(name);
            cardToThrow.setImage(image);
            deck.getTableCards().add(cardToThrow);
            deck.getPcCards().remove(cardToThrow);
        }

        if (deck.getTableCards().isEmpty() && deck.getLastTake() == 1) {
            deck.setPcNumOfCards(deck.getPcNumOfCards()+1);
        }

        if (deck.getBound() == 0 && deck.getPcCards().size() == 0) calculateLastTake(deck.getLastTake());
    }

    // List of all posible comibantios left on the table
    List<HashSet<Card>> combinations(List<Card> tableCards) {
        int n = tableCards.size();
        List<HashSet<Card>> listOfCombinations = new ArrayList<>();
        for (int i = 1; i < (1 << n); i++) {
            HashSet<Card> list = new HashSet<>();
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    list.add(tableCards.get(j));
                }
            }
            listOfCombinations.add(list);
        }
        return listOfCombinations;
    }

    // Calculates the number of points (stihovi)
    public int countScore(HashSet<Card> cards, Card selectedCard) {
        int cntScore = 0;
        for (Card card : cards) {
            cntScore += deck.calculateScore(card);
        }
        cntScore += deck.calculateScore(selectedCard);
        return cntScore;
    }

    // Calculates the last take and add to the score everythig that is left
     public void calculateLastTake(int last) {
        int points = 0;
        for (Card tableCard : deck.getTableCards()) {
            points += deck.calculateScore(tableCard);
        }
        if (last == 1) {
            deck.setPcNumOfCards(deck.getPcNumOfCards() + deck.getTableCards().size());
            deck.setPcScore(deck.getPcScore() + points);
        }
        else {
            deck.setMyNumOfCards(deck.getMyNumOfCards() + deck.getTableCards().size());
            deck.setMyScore(deck.getMyScore() + points);
        }
    }
}
