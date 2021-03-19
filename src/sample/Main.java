package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Tablic");
        window.setScene(MainView.makeScene());
        window.centerOnScreen();
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
