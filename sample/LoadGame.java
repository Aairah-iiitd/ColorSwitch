package sample;

public class LoadGame {
    private String lowerObstacle;
    private String upperObstacle;
    private int score;
    public LoadGame(String lowerObstacle,String upperObstacle,int score)
    {
        this.lowerObstacle=lowerObstacle;
        this.upperObstacle=upperObstacle;
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public String getLowerObstacle() {
        return lowerObstacle;
    }

    public String getUpperObstacle() {
        return upperObstacle;
    }

    public void setLowerObstacle(String lowerObstacle) {
        this.lowerObstacle = lowerObstacle;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUpperObstacle(String upperObstacle) {
        this.upperObstacle = upperObstacle;
    }
}


