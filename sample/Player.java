package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private ArrayList<GameBase> saved = new ArrayList<GameBase>();
    private  int highscore;

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getHighscore() {
        return highscore;
    }

    public void addGame(GameBase base){
        saved.add(base);
        if(saved.size()>5)
            saved.remove(0);

    }

    public ArrayList<GameBase> getSaved() {
        return saved;
    }

    public void printGames(){
        System.out.println("Saved Games:");
        for (int i = 0; i < saved.size(); i++){
            saved.get(i).print();
        }
    }

    @Override
    public String toString() {
        return "Player{"+"games"+saved.size()+
                ", highscore=" + highscore +
                '}';
    }
}
