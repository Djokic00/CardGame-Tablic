package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import model.Card;
import model.DeckOfCards;
import view.MainView;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class MyController implements EventHandler<ActionEvent> {
    MainView mainView;
    SumValidator sumValidator;

    public MyController(MainView mainView) {
        this.mainView = mainView;
        this.sumValidator = new SumValidator();
    }

    @Override
    public void handle(ActionEvent event) {
        Card selectedCard = mainView.getSelectedCard();
        if (selectedCard == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You must select a card! \n" +
                    "Try again.");
            alert.show();
            return;
        }
        HashSet<Card> selectedTableCards = mainView.getSelectedTableCards();
        int cntCards;
        int cntScore = 0;
        DeckOfCards deck = DeckOfCards.getInstance();

        if (selectedTableCards.isEmpty()) {
            deck.getTableCards().add(selectedCard);
            deck.getMyCards().remove(selectedCard);
            mainView.pcTurn();
            return;
        }
        else if (sumValidator.isValidSum(selectedCard.getValue(), selectedTableCards)) {
            cntCards = selectedTableCards.size();
            deck.setMyNumOfCards(deck.getMyNumOfCards() + cntCards + 1);

            for (Card card : selectedTableCards) {
                cntScore += deck.calculateScore(card);
                deck.getTableCards().remove(card);
            }
            cntScore += deck.calculateScore(selectedCard);
            deck.setMyScore(deck.getMyScore() + cntScore);

            deck.getMyCards().remove(selectedCard);
            deck.setLastTake(0);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The sum of cards is not right! \n" +
                    "Try again.");
            alert.show();
            return;
        }

        if (deck.getTableCards().isEmpty() && deck.getLastTake() == 0) {
            deck.setMyNumOfCards(deck.getMyNumOfCards() + 1);
        }

        mainView.pcTurn();

    }

}
