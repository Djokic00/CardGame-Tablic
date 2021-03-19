package view;

import controller.PcController;
import controller.MyController;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Card;
import model.DeckOfCards;
import sample.Main;
import java.util.*;

public class MainView extends HBox {
    private Image backGroundImage;
    private PcController pcController;
    private DeckOfCards deck = DeckOfCards.getInstance();
    private Button playBtn;
    private HashSet<Card> selectedTableCards;
    private Card selectedCard;
    private VBox vb;

    public static Scene makeScene() {
        return new Scene(new MainView(), 1200, 1000);
    }

    public MainView() {
        initElements();
        addElements();
        initListeners();
    }

    private void initElements() {
        backGroundImage = new Image("slike/green.jpg");
        playBtn = new Button("Play");
        playBtn.setPrefSize(150, 80);
        playBtn.setFont(Font.font(28));
        pcController = PcController.getInstance();
        clearCards();
    }

    private void addElements() {

        HBox hb1 = new HBox();
        hb1.setAlignment(Pos.CENTER);
        hb1.setSpacing(10);


        if (deck.getMyCards().isEmpty() && deck.getPcCards().isEmpty()) {
            if (deck.getBound() == 0) {
                Main.window.setScene(EndGameView.makeScene());
                return;
            }
            deck.makeMyCards();
            deck.makePcCards();
        }

        for (Card card : deck.getPcCards()) {
            ImageView imageView = new ImageView();
            imageView.setImage(card.getImage());
            imageView.setFitHeight(153);
            imageView.setFitWidth(100);
            hb1.getChildren().add(imageView);
        }

        HBox hb2 = new HBox();
        hb2.setAlignment(Pos.CENTER);
        hb2.setSpacing(10);

        for (Card card : deck.getTableCards()) {
            ImageView imageView = new ImageView();
            imageView.setImage(card.getImage());
            imageView.setFitHeight(153);
            imageView.setFitWidth(100);
            //cardToImage.put(card, imageView);

            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (selectedTableCards.contains(card)) {
                    selectedTableCards.remove(card);
                    imageView.setEffect(null);
                } else {
                    selectedTableCards.add(card);
                    imageView.setEffect(new DropShadow(20, Color.YELLOW));
                }
            });

            hb2.getChildren().add(imageView);
        }

        HBox hb3 = new HBox();
        hb3.setAlignment(Pos.CENTER);
        hb3.setSpacing(10);

        for (Card card : deck.getMyCards()) {
            ImageView imageView = new ImageView();
            imageView.setImage(card.getImage());
            imageView.setFitHeight(153);
            imageView.setFitWidth(100);
            imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                if (selectedCard == null) {
                    selectedCard = card;
                    imageView.setEffect(new DropShadow(50, Color.YELLOW));
                } else if (selectedCard.equals(card)) {
                    selectedCard = null;
                    imageView.setEffect(null);
                }
            });
            hb3.getChildren().add(imageView);
        }

        BackgroundImage backgroundimage = new BackgroundImage(backGroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundimage);

        vb = new VBox();
        vb.setSpacing(200);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(hb1, hb2, hb3);

        this.setBackground(background);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);
        this.getChildren().addAll(vb, playBtn);

    }

    public void updateCanvas() {
        clearCards();
        this.getChildren().removeAll(vb, playBtn);
        addElements();
    }

    public void pcTurn() {
        updateCanvas();
        PauseTransition wait = new PauseTransition(Duration.seconds(2));
        wait.setOnFinished((e) -> {
            pcController.play();
            updateCanvas();
        });
        wait.play();
    }

    public void clearCards() {
        selectedCard = null;
        selectedTableCards = new HashSet<>();
    }

    private void initListeners() {
        playBtn.setOnAction(new MyController(this));
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public HashSet<Card> getSelectedTableCards() {
        return selectedTableCards;
    }

}
