package sample;

public abstract class Obstacle{
    protected String type;          //to identify the type of obstacle while updating base
    protected boolean rotateType;
    protected double height;
    protected Star star;
    protected Switcher switcher;
    protected PowerUp power;
    protected Super superstar;
    protected double speed;
    protected double currentAngle=0;
    protected double distance=0;


    public abstract void move();
    public abstract boolean isCollision(Ball ball);

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public Star getStar() {
        return star;
    }

    public void setSuper(Super superstar) {
        this.superstar = superstar;
    }

    public Super getSuper() {
        return superstar;
    }

    public void setPower(PowerUp power){
        this.power = power;
    }

    public PowerUp getPower(){
        return this.power;
    }


    public Switcher getSwitcher() {
        return switcher;
    }

    public void setSwitcher(Switcher switcher) {
        this.switcher = switcher;
    }
    public void scroll(double y)
    {
        if(star!=null)
            star.setLayoutY(star.getLayoutY()-y);
        if(switcher!=null)
            switcher.setLayoutY(switcher.getLayoutY()-y);
        if(power!=null)
            power.setLayoutY(power.getLayoutY()-y);
        if(superstar!=null)
            superstar.setLayoutY(superstar.getLayoutY()-y);
        height-=y;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public double getDistance() {
        return distance;
    }
}
