package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setTitle("ChimbaGames");
            stage.setScene(new Scene(root));
            Image icon = new Image("Images/icon.png");
            stage.getIcons().add(icon);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
