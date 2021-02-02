package sample;

import java.io.Serializable;

public class GameBase implements Serializable {
    private String obstacle[];
    private double ball_height, obstacleHeight;
    private double orientation[];
    private int stars=3;
    int score;

    GameBase(){
        obstacle=new String[3];
        obstacle[0]="ring";
        obstacle[1]="cross";
        obstacle[2]="bars";
        obstacleHeight=600;
        ball_height = 800;
        score = 0;
        orientation=new double[3];
    }

    public String[] getObstacle() {
        return obstacle;
    }

    public int getScore(){
        return score;
    }

    public double getBallheight(){
        return ball_height;
    }

    public double getObstacleHeight() {
        return obstacleHeight;
    }

    public double[] getOrientation() {
        return orientation;
    }


    public void update(int score, double bHeight, double oHeight, String type[], double motion[],int stars){
        this.ball_height = bHeight;
        this.obstacleHeight = oHeight;
        this.score=score;
        this.stars=stars;
        for(int i=0;i<3;i++)
        {
            this.orientation[i]=motion[i];
            obstacle[i]=type[i];
        }
       print();
    }

    public int getStars() {
        return stars;
    }

    public void print(){
        System.out.println("Base:\nScore: " + score + "\nBall Height: " + ball_height + "\nObstacle Height: " + obstacleHeight + "\nobstacle1: " + obstacle[0] + "\nobstacle2: " + obstacle[1]+"\nobstacle3: "+obstacle[2]+"\nvisible stars: "+stars);
        for(int i=0;i<orientation.length;i++)
            System.out.println(orientation[i]);
    }
}
