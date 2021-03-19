package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.DeckOfCards;
import sample.Main;

public class EndGameView extends VBox {

    private Label title;
    private Label winnerName;
    private Label computerScore;
    private Label playerScore;
    private DeckOfCards deck = DeckOfCards.getInstance();

    public static Scene makeScene() {
        Scene scene = new Scene(new EndGameView(),700,700);
        Main.window.setScene(scene);
        Main.window.centerOnScreen();
        return scene;
    }

    public EndGameView() {
        initElements();
        addElements();
    }

    private void initElements() {
        String winner = deck.calculateFinalPoints();
        int myScore = deck.getMyScore();
        int myNumOfCards = deck.getMyNumOfCards();
        int pcScore = deck.getPcScore();
        int pcNumOfCards = deck.getPcNumOfCards();
        System.out.println(pcNumOfCards);

        title = new Label("Winner :");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font(36));
        winnerName = new Label(winner);
        winnerName.setFont(Font.font(36));
        winnerName.setTextFill(Color.WHITE);

        String score = String.valueOf(pcScore);
        String num = String.valueOf(pcNumOfCards);
        computerScore = new Label( "PC :\nNumber of taken points: " + score + " " + " \nNumber of taken cards: " + num);
        computerScore.setTextFill(Color.WHITE);
        computerScore.setFont(Font.font(22));
        String lScore = String.valueOf(myScore);
        String lNum = String.valueOf(myNumOfCards);
        playerScore = new Label("Player :\nNumber of taken points: " + lScore + " " + " \nNumber of taken cards: " + lNum);
        playerScore.setTextFill(Color.WHITE);
        playerScore.setFont(Font.font(22));
    }

    private void addElements() {

        VBox vb1 = new VBox();
        vb1.setSpacing(50);
        vb1.setAlignment(Pos.CENTER);
        vb1.getChildren().addAll(title, winnerName);

        HBox hb1 = new HBox();
        hb1.setSpacing(100);
        hb1.setAlignment(Pos.CENTER_LEFT);
        hb1.getChildren().addAll(playerScore);

        HBox hb2 = new HBox();
        hb2.setSpacing(100);
        hb2.setAlignment(Pos.CENTER_RIGHT);
        hb2.getChildren().addAll(computerScore);

        HBox hb1_hb2 = new HBox();
        hb1_hb2.setSpacing(200);
        hb1_hb2.setAlignment(Pos.CENTER);
        hb1_hb2.getChildren().addAll(hb1,hb2);

        this.setSpacing(100);
        this.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vb1,hb1_hb2);
    }

}
