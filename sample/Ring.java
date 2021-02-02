package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Ring extends Obstacle {
    private ArrayList<Arc> arcs=new ArrayList<>();
    private Rotate rotate;
    private double size;
    public Ring(double height,double size,double speed,double rot)
    {
        setHeight(height);
        setSpeed(speed);
        this.size=size;
        rotateType=true;
        type = "ring";
        rotate =new Rotate(0,500,height);
        power = null;
        superstar = null;
        star = null;
        setSwitcher(new Switcher(height-200));
        currentAngle =rot;
        arcs.add(create(0,Coloring.getColor(0)));
        arcs.add(create(90,Coloring.getColor(1)));
        arcs.add(create(180,Coloring.getColor(2)));
        arcs.add(create(270,Coloring.getColor(3)));
    }
    public Arc create(double start, Color color)
    {

        Arc arc=new Arc(500,getHeight(),size,size,start,90);    //start=starting degree
        arc.setStroke(color);
        arc.setStrokeWidth(20);
        arc.setFill(Color.TRANSPARENT);
        arc.getTransforms().add(rotate);
        return arc;
    }
    @Override
    public void move()
    {
        currentAngle+=speed;
        rotate.setAngle(currentAngle);
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    @Override
    public boolean isCollision(Ball ball)
    {
        boolean b=false;
        for(int i=0;i<4;i++)
        {
            if (Shape.intersect(ball, arcs.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i)))
            {
                b=true;
                break;
            }

        }
        return b;

    }

    public void scroll(double y)
    {
        super.scroll(y);
        for(Arc arc:arcs)
        {
            arc.setLayoutY(arc.getLayoutY()-y);
            //System.out.println(arcs.get(0).layoutYProperty());
        }
    }
}
