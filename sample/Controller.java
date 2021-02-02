package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    static GameBase base = new GameBase();

    public void play()
    {
        if(Main.play==true)
        {
            Media m=new Media(new File("src\\sample\\Sound\\button.wav").toURI().toString());
            MediaPlayer media = new MediaPlayer(m);
            media.play();
        }
    }

    public void ModeScreen(ActionEvent event) throws IOException {
        play();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\mode.fxml"));
        window.setScene(new Scene(root,600,800,Color.BLACK));
        window.show();
        if(Main.play==true)
            Main.mp.play();
    }

    public void MultiGame(ActionEvent event) {
        play();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Game game=new Game(primaryStage);
        Main.mp.stop();
    }

    public void NewGame(ActionEvent event) {
        play();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        base = new GameBase();
        Game game=new Game(primaryStage, base,true);
        base = Game.base;
        //Game game=new Game(primaryStage,new LoadGame("sample.Cross","sample.Bars",5));
        Main.mp.stop();
    }
    public void BackToGame(ActionEvent event) {
        play();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Game game=Main.game;
        primaryStage.setScene(Main.game.getScene());
        primaryStage.show();
        game.then=System.nanoTime();
        //game.start();
        Main.mp.stop();
    }

    public void Resurrect(ActionEvent event) {
        play();
        if (Main.total.getTotal() >= 5) {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Game game = new Game(primaryStage, base, false);
            base = Game.base;
            Main.total.addstar(-5);
            Main.mp.stop();
        }
    }

    public void ResumeGame(ActionEvent event) throws IOException, ClassNotFoundException {
        Main.player = Serialise.deserialise();
        Main.player.printGames();
        play();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\resume.fxml"));
        window.setScene(new Scene(root,600,800,Color.BLACK));
        window.show();
        ListView<String> list=(ListView<String>)root.lookup("#list");
        list.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);

                        // decide to add a new styleClass
                        // getStyleClass().add("costume style");
                        // decide the new font size
                        setFont(Font.font(16));
                    }
                }
            };
        });
        ArrayList<GameBase> saved =Main.player.getSaved();
        for(int i=0;i<saved.size();i++)
        {
            list.getItems().add(i+1+". Score: "+saved.get(i).getScore());
        }
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2) {
                    int n=list.getSelectionModel().getSelectedIndex();
                    base=saved.get(n);
                    Game game=new Game(window, base,false);
                    base = Game.base;
                    saved.remove(n);

                }
            }
        });

    }

    public void MainMenu(ActionEvent event) throws IOException {
        play();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\menu.fxml"));
        window.setScene(new Scene(root,600,800,Color.BLACK));
        window.show();
        if(Main.play==true)
            Main.mp.play();
    }

    public void HelpScreen(ActionEvent event) throws IOException {
        play();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\help.fxml"));
        window.setScene(new Scene(root,600,800,Color.BLACK));
        window.show();
        if(Main.play==true)
            Main.mp.play();
    }

    public void ShowAchievements(ActionEvent event) throws IOException {
        play();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root2 = FXMLLoader.load(getClass().getResource("FXML\\achievements.fxml"));
        try {
            Label labelx2 = (Label)root2.lookup("#highScore");
            labelx2.setText(String.valueOf(Main.player.getHighscore()));
            Label labely2 = (Label)root2.lookup("#totalStars");
            labely2.setText(String.valueOf(Main.total.getTotal()));
        }catch (Exception e){
            e.printStackTrace();
        }
        window.setScene(new Scene(root2,600,800,Color.BLACK));
        window.show();
        if(Main.play==true)
            Main.mp.play();
    }

    public void PauseGame(ActionEvent event) throws IOException {
        Main.player.addGame(base);
        Serialise.serialise(Main.player);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("FXML\\menu.fxml"));
        window.setScene(new Scene(root,600,800,Color.BLACK));
        window.show();
    }

    public void ReloadGame(ActionEvent event) throws IOException, ClassNotFoundException {
        Main.player = Serialise.deserialise();
        Main.player.printGames();
    }

    public void media(ActionEvent actionEvent) {
        play();
        if(Main.play==true)
        {
            Main.mp.pause();
            Main.play=false;
        }

        else
        {
            Main.mp.play();
            Main.play=true;
        }
    }
}