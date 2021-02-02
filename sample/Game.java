package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Group {
    private ArrayList<Obstacle> obstacles=new ArrayList<>();
    private Ball ball;
    private Ball ball1;
    int counter = 0;
    int immunity;
    static GameBase base;
    private int score;
    private Random random;
    private Label label;
    public long then=System.nanoTime();
    private Stage primaryStage;
    private double height;
    private int prev=2;
    private ArrayList<Line> marker=new ArrayList<>();
    private boolean add=false;
    private double refresh=40;
    private double gameSpeed=1;
    private boolean pause;
    private int num;


    public Game(Stage primaryStage, GameBase gb,boolean isNew)
    {
        score = gb.getScore();
        height= gb.getObstacleHeight();
        ball=new Ball(this, gb.getBallheight(),false);
        this.base = gb;
        random=new Random();
        getChildren().add(ball);
        if(isNew)
        {
            pause=false;
            immunity=0;
        }

        else
        {
            pause=true;
            immunity=1;
        }

        for(int i=0;i<3;i++)
        {
            double d=gb.getOrientation()[i];
            String s=gb.getObstacle()[i];
            Boolean b=((3-i)>gb.getStars());
            switch (s)
            {
                case "bars":
                    addBars(d,b,false);
                    break;
                case "ring":
                    addRing(d,b,false);
                    break;
                case "cross":
                    addCross(d,b,false);
                    break;
                case "square":
                    addSquare(d,b,false);
                    break;
                case "nestedRing":
                    addRing(d,b,false);
                    break;
                case "doubleCross":
                    addDoubleCross(d,b,false);
                    break;
                case "squareInCircle":
                    addSquareInCircle(d,b,false);
                    break;
                case "doubleRing":
                    addDoubleRing(d,b,false);
                    break;
                case "rails":
                    addRails(d,b,false);
                    break;

            }
            height-=400;
            System.out.println(height);
            num+=1;
        }


        for(int i = 3;i < refresh; i++)
            addObstacle(false);
        counter=(3-gb.getStars());

        addcanvas();
        addLabel();
        this.primaryStage=primaryStage;
        Scene gameScene = new Scene(this,1000,1000, Color.BLACK);
        gameScene.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.UP))
            {
                if(Main.play==true)
                {
                    play("jump");
                }

                ball.moveUp();
            }

            else if(e.getCode().equals(KeyCode.P))
            {
                pause=true;
            }
            else if(e.getCode().equals(KeyCode.O))
            {
                pause=false;
                ball.setGravity(false);
                this.start();
            }
            else if(e.getCode().equals(KeyCode.Q))
            {
                System.out.println(obstacles.get(counter).getHeight());
            }

        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
        start();

    }
    public Game(Stage primaryStage)
    {
        height=600;
        num=0;
        ball=new Ball(this, 800,true);
        ball1=new Ball(this, 800,true);
        ball1.setFill(Coloring.getColor(2));
        ball.setCenterX(520);
        ball1.setCenterX(480);
        random=new Random();
        getChildren().addAll(ball,ball1);
        gameSpeed=1;
        for(int i = 0 ;i < refresh; i++)
            addObstacle(true);
        addLabel1();
        this.primaryStage=primaryStage;
        Scene gameScene = new Scene(this,1000,1000, Color.BLACK);
        gameScene.setOnKeyPressed(e ->{
            if(e.getCode().equals(KeyCode.UP))
            {
                ball.moveUp();
            }

            else if(e.getCode().equals(KeyCode.W))
            {
                ball1.moveUp();
            }
        });
        primaryStage.setScene(gameScene);
        primaryStage.show();
        start1();
    }






    public void start()
    {
        Main.game=this;

        AnimationTimer timer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(pause==true)
                    this.stop();
                if(add)
                {
                    height=-200;
                    for(int i=0;i<refresh;i++)
                        addObstacle(false);
                    add=false;
                }
                update((now-then)/800000000.0,ball);
                boolean a=false;
                a=hitObstacle(ball,false);
                //a=false;
                if(a && immunity == 0)
                {
                    this.stop();
                    try {
                        Main.total.saveStars();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Total Stars:" + Main.total.getTotal());
                    play("dead");
                    if (score > Main.player.getHighscore())
                        Main.player.setHighscore(score);
                    try {
                        Serialise.serialise(Main.player);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = null;

                    try {
                        root = FXMLLoader.load(getClass().getResource("FXML\\over.fxml"));
                        Label label = (Label)root.lookup("#score");
                        label.setText(String.valueOf(score));
                        Label labelx = (Label)root.lookup("#highscore");
                        labelx.setText(String.valueOf(Main.player.getHighscore()));
                        Label labely = (Label)root.lookup("#totalStar");
                        labely.setText(String.valueOf(Main.total.getTotal()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(root,600,800,Color.BLACK));
                }

                hitItem();

            }

        };

        timer.start();

    }

    public void start1()
    {
        Main.game=this;

        AnimationTimer timer=new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(ball.getCenterY()<=ball1.getCenterY())
                    label.setText("Winner: "+"UP");
                else
                    label.setText("Winner: "+"W");
                boolean a=false;
                boolean b=false;
                update((now-then)/800000000.0,ball);
                update((now-then)/800000000.0,ball1);
                a=hitObstacle(ball,true);
                b=hitObstacle(ball1,true);
                if(!a)
                a= checkHeight(ball);
                if(!b)
                b=checkHeight(ball1);
                if(a || b)
                {
                    this.stop();
                    play("dead");
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("FXML\\over1.fxml"));
                        Label label = (Label)root.lookup("#winner");
                        if(a)
                            label.setText("W");
                        else
                            label.setText("UP");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    primaryStage.setScene(new Scene(root,600,800,Color.BLACK));
                }
                hitItem1(ball);
                hitItem1(ball1);

            }

        };

        timer.start();

    }
    public boolean checkHeight(Ball b)
    {
        if(b.getCenterY()>950)
            return true;
        return false;
    }

    public int getScore() {
        return score;
    }
    public void addLabel1()
    {
        label=new Label("Winner: "+"UP");
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Times New Roman",30));
        getChildren().add(label);
    }

    public void addLabel()
    {
        label=new Label("Score: "+score +"\nHP: " + immunity);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("Times New Roman",30));
        getChildren().add(label);
    }
    public void addLine(double height)
    {
        Line l=new Line(0,600-height,1000,600-height);
        marker.add(l);
        l.setStroke(Color.WHITE);
        l.setStrokeWidth(5);
        getChildren().add(l);
    }
    public void play(String s)
    {
        Media m=null;
        switch(s)
        {
            case "button":
                m=new Media(new File("src\\sample\\Sound\\button.wav").toURI().toString());
                break;
            case "switcher":
                m=new Media(new File("src\\sample\\Sound\\colorswitch.wav").toURI().toString());
                break;
            case "jump":
                m=new Media(new File("src\\sample\\Sound\\jump.wav").toURI().toString());
                break;
            case "star":
                m=new Media(new File("src\\sample\\Sound\\star.wav").toURI().toString());
                break;
            case "dead":
                m=new Media(new File("src\\sample\\Sound\\dead.wav").toURI().toString());
                break;

        }
        if(Main.play==true)
        {
            MediaPlayer media = new MediaPlayer(m);
            media.play();
        }

    }
    public void addObstacle(boolean multi)
    {
        Random random=new Random();
        int n;
        int m=0;
        if(num>4)
            m=2;
        if(num>8)
            m=3;
        if(num>12)
            m=5;
        if(multi)
        {
            while((n=random.nextInt(9))==prev);
            {
            }
        }
        else
        {
            while((n=random.nextInt(4)+m)==prev);
            {
            }
        }
        prev=n;
        switch(n)
        {
            case 0: addRing(0,false,multi);
                    break;
            case 1: addCross(0,false,multi);
                    break;
            case 2: addBars(0,false,multi);
                    break;
            case 3: addSquare(0,false,multi);
                    break;
            case 4: addDoubleCross(0,false,multi);
                    break;
            case 5: addNestedRing(0,false,multi);
                    break;
            case 6: addSquareInCircle(0,false,multi);
                    break;
            case 7: addDoubleRing(0,false,multi);
                    break;
            case 8: addRails(0,false,multi);
                    break;
        }


        height-=400;
        num++;
        System.out.println(height);
        //System.out.println(obstacles.get(obstacles.size()-1).getClass().getName());

    }

    public void addSquare(double rot,boolean beaten,boolean multi)
    {   Obstacle obstacle=new Square(height,90,gameSpeed,rot);
        obstacles.add(obstacle);
        for(Line line:((Square)obstacle).getLines())
            getChildren().add(line);
        if(multi)
        {
          getChildren().add(obstacle.getSwitcher());
          return;
        }

        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }

        }
        else
            obstacle.setSwitcher(null);
    }

    public void addRing(double rot,boolean beaten,boolean multi) {
        Obstacle obstacle = new Ring(height, 100,gameSpeed,rot);
        obstacles.add(obstacle);
        for (Arc arc : ((Ring) obstacle).getArcs())
            getChildren().add(arc);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);

    }

    public void addBars(double dist,boolean beaten,boolean multi)
    {
        Obstacle obstacle=new Bars(height,100,gameSpeed,dist);
        obstacles.add(obstacle);
        for(Line line:((Bars)obstacle).getLines())
            getChildren().add(line);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setStar(new Star(obstacle.getHeight()));
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);

    }

    public void addCross(double rot,boolean beaten,boolean multi)
    {
        Obstacle obstacle=new Cross(height,100,gameSpeed,rot);
        obstacles.add(obstacle);
        for(Line line:((Cross)obstacle).getLines())
            getChildren().add(line);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);

    }

    public void addDoubleCross(double rot,boolean beaten,boolean multi)
    {
        Obstacle obstacle=new DoubleCross(height,100,gameSpeed,rot);
        obstacles.add(obstacle);
        for(Line line:((DoubleCross)obstacle).getLines())
            getChildren().add(line);
        for(Line line:((DoubleCross)obstacle).getRightLines())
            getChildren().add(line);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight() - 100));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);
    }

    public void addNestedRing(double rot,boolean beaten,boolean multi)
    {
        Obstacle obstacle = new NestedRing(height, 120,gameSpeed,rot);
        obstacles.add(obstacle);
        for (Arc arc : ((NestedRing) obstacle).getArcs())
            getChildren().add(arc);
        for (Arc arc : ((NestedRing) obstacle).getInnerarcs())
            getChildren().add(arc);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);
    }

    public void addSquareInCircle(double rot,boolean beaten,boolean multi)
    {
        Obstacle obstacle = new SquareInCircle(height, 110,gameSpeed,rot);
        obstacles.add(obstacle);
        for (Arc arc : ((SquareInCircle) obstacle).getArcs())
            getChildren().add(arc);
        for (Line line : ((SquareInCircle) obstacle).getLines())
            getChildren().add(line);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);

    }

    public void addDoubleRing(double rot,boolean beaten,boolean multi)
    {
        Obstacle obstacle = new DoubleRing(height, 100,gameSpeed,rot);
        obstacles.add(obstacle);
        for (Arc arc : ((DoubleRing) obstacle).getArcs())
            getChildren().add(arc);
        for (Arc arc : ((DoubleRing) obstacle).getInnerarcs())
            getChildren().add(arc);
        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);
    }

    public void addRails(double dist,boolean beaten,boolean multi)
    {
        Obstacle obstacle=new Rails(height,100,gameSpeed,dist);
        obstacles.add(obstacle);
        for(Line line:((Rails)obstacle).getUpperLines())
            getChildren().add(line);
        for(Line line:((Rails)obstacle).getLowerLines())
            getChildren().add(line);

        if(multi)
        {
            getChildren().add(obstacle.getSwitcher());
            return;
        }
        if(!beaten)
        {
            int p = random.nextInt(10);
            if (p % 9 != 0 && p % 8 != 0) {
                obstacle.setStar(new Star(obstacle.getHeight()));
                getChildren().addAll(obstacle.getStar(),obstacle.getSwitcher());
            }
            else if (p % 8 == 0){
                obstacle.setSuper(new Super(obstacle.getHeight()));
                getChildren().addAll(obstacle.getSuper(),obstacle.getSwitcher());
            }
            else {
                obstacle.setStar(new Star(obstacle.getHeight()));
                obstacle.setPower(new PowerUp(obstacle.getHeight() - 150));
                getChildren().addAll(obstacle.getStar(), obstacle.getSwitcher(), obstacle.getPower());
            }
        }
        else
            obstacle.setSwitcher(null);
    }



    public void update(double t,Ball ball)
    {
        ball.moveDown(t);
        for(Obstacle obstacle:obstacles)
            obstacle.move();
        if(ball.getCenterY()<300 && ball.getMotion())
        {
            for(Obstacle obstacle:obstacles)
                obstacle.scroll(-3);
        }
        else if(ball.getCenterY()<400 && ball.getMotion())
        {
            for(Obstacle obstacle:obstacles)
                obstacle.scroll(-2);
        }
    }

    public Ball getBall() {
        return ball;
    }
    public boolean hitObstacle(Ball ball,boolean multi)
    {
        boolean hit=false;
        for(int i=0;i<obstacles.size();i++)
        {
            Obstacle obstacle=obstacles.get(i);
            if(obstacle.isCollision(ball))
            {
                hit=true;
                break;
            }

        }
        if(multi)
            return hit;
        if(hit)
            saveState();
        return hit;
    }
    public void saveState()
    {
        for(int i=0;i<obstacles.size();i++)
        {
            if(obstacles.get(i).getHeight()<=1100)
            {
                int n=0;
                String[] type=new String[3];
                double[] motion=new double[3];
                for(int j=0;j<Math.min(3,obstacles.size()-i);j++)
                {
                    Obstacle obs=obstacles.get(i+j);
                    if(obs.getStar()!=null)
                        n++;
                    type[j]=obs.type;
                    if(obs.rotateType)
                        motion[j]=obs.getCurrentAngle();
                    else
                        motion[j]=obs.getDistance();
                }
                System.out.println("******\t"+n+"\t*****");
                if(type[0]==null)
                    type[0]="ring";
                if(type[1]==null)
                    type[1]="cross";
                if(type[2]==null)
                    type[2]="bars";

                base.update(score,ball.getCenterY(),obstacles.get(i).getHeight(),type,motion,n);
                break;


            }
        }

    }
    public void hit1(Obstacle obstacle,Ball ball)
    {
        if(obstacle.getSwitcher()!=null && obstacle.getSwitcher().isCollision(ball))
        {
            int n;
            play("switcher");
            while(Coloring.getColor(n=random.nextInt(4))==ball.getFill())
            {
            }
            ball.setFill(Coloring.getColor(n));
            ball.setColor(Coloring.getColor(n));
            getChildren().remove(obstacle.getSwitcher());
            obstacle.setSwitcher(null);
        }

    }
    public void hit(Obstacle obstacle)
    {
        int s=score;
        if(obstacle.getStar()!=null && obstacle.getStar().isCollision(ball))
        {

            score++;
            Main.total.addstar(1);
            if (immunity > 0){
                immunity--;
            }


            play("star");
            label.setText("Score: "+score +"\nHP: " + immunity);
            getChildren().remove(obstacle.getStar());
            obstacle.setStar(null);
        }
        if(obstacle.getSuper()!=null && obstacle.getSuper().isCollision(ball))
        {
            score = score + 2;
            Main.total.addstar(2);
            if (immunity > 0){
                immunity--;
            }
            play("star");
            label.setText("Score: "+score +"\nHP: " + immunity);
            getChildren().remove(obstacle.getSuper());
            obstacle.setSuper(null);
        }
        if(obstacle.getPower()!=null && obstacle.getPower().isCollision(ball))
        {
            System.out.println("Power touch");
            immunity = 2;
            getChildren().remove(obstacle.getPower());
            obstacle.setPower(null);
            label.setText("Score: "+score +"\nHP: " + immunity);
        }
        if(obstacle.getSwitcher()!=null && obstacle.getSwitcher().isCollision(ball))
        {
            int n;counter++;
            System.out.println("Counter value:" + counter);
            if(counter%refresh==0)
                add=true;
            play("switcher");
            while(Coloring.getColor(n=random.nextInt(4))==ball.getFill())
            {
            }
            ball.setFill(Coloring.getColor(n));
            ball.setColor(Coloring.getColor(n));
            getChildren().remove(obstacle.getSwitcher());
            obstacle.setSwitcher(null);
        }

        if(score/5>s/5)
        {
            gameSpeed+=0.5;
            System.out.println("\nspeed up :D\n");
            for(Obstacle obstacle1:obstacles)               //difficulty++
                obstacle1.setSpeed(gameSpeed);

        }



    }

    public void hitItem()
    {
       for(Obstacle obstacle:obstacles)
       {
           hit(obstacle);
       }
    }
    public void hitItem1(Ball ball)
    {
        for(Obstacle obstacle:obstacles)
        {
            hit1(obstacle,ball);
        }
    }

    public void addcanvas()
    {

        Canvas canvas = new Canvas(250,250);
        canvas.setTranslateX(700);
        canvas.setTranslateY(20);
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        Image image;
        try {
            image = new Image(new FileInputStream("src\\sample\\Image\\button.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        canvas.getGraphicsContext2D().drawImage(image,20,20);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                play("button");
                saveState();
                pause=true;
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("FXML\\pause.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root,600,800,Color.BLACK));
                window.show();
            }
        });
        getChildren().add(canvas);
    }
}
