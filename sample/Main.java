package sample;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class Main extends Application{
    public static MediaPlayer mp;
    public static boolean play=true;
    public static Game game;
    static  Player player = new Player();
    static TotalStars total;
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO
        try{
            total = TotalStars.getStars();
            player= Serialise.deserialise();
        }catch (Exception e){
            total = new TotalStars();
            player=new Player();
        }
        File f = new File("src\\sample\\Sound\\time.mp3");
        Media m = new Media(f.toURI().toString());
        mp = new MediaPlayer(m);
        mp.play();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\menu.fxml"));
        primaryStage.setScene(new Scene(root,600,800,Color.BLACK));
        primaryStage.setTitle("Color Switch");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
