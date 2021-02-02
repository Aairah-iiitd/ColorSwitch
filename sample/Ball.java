package sample;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;


public class Ball extends Circle {
    private Color color;
    private double velocity;
    private double height;
    private boolean gravity;
    private boolean multi;
    public boolean getMotion(){
        return gravity;
    }
    private Game game;
    public Ball(Game game,double height,boolean multi)
    {
        super(500,height,10);
        this.game=game;
        this.multi=multi;
        color=Coloring.getColor(0);
        setFill(color);
        velocity=0;
        gravity=false;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public void moveUp()
    {
        gravity=true;
        if(velocity<0)
            velocity-=5;
        velocity=-5;
        setCenterY(getCenterY()+velocity);
        game.then= System.nanoTime();
    }
    public void moveDown(double t)

    {
        if(gravity==true)
        {
            if(getCenterY()<800 || multi) {
                velocity += 1.8 * t;
                height=getCenterY() + velocity;
                setCenterY(height);
            }
            else
                setCenterY(800);
        }

    }
    public void changeColor()
    {
        Random random =new Random();
        color=Coloring.getColor(random.nextInt(4));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {


    }
}
