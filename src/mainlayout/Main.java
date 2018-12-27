package mainlayout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainlayout.fxml"));
        primaryStage.setTitle("Bible-it");
        primaryStage.setScene(new Scene(root, 1000.0D, 900.0D));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
