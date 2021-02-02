package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class DoubleRing extends Obstacle {
    private ArrayList<Arc> arcs= new ArrayList<>();
    private ArrayList<Arc> innerarcs = new ArrayList<>();
    private Rotate rotate, rotate2;
    private double currentAngle2;
    private double size;
    public DoubleRing(double height,double size,double speed,double rot)
    {
        rotateType=true;
        setHeight(height);
        setSpeed(speed);
        this.size=size;
        type = "doubleRing";
        rotate =new Rotate(0,500-(size+10),height);
        rotate2 =new Rotate(0,500+(size-35+10),height);
        power = null;
        superstar = null;
        star = null;
        setSwitcher(new Switcher(height-200));
        currentAngle =rot;
        currentAngle2=-rot;
        arcs.add(create(45,Coloring.getColor(0)));
        arcs.add(create(135,Coloring.getColor(1)));
        arcs.add(create(225,Coloring.getColor(2)));
        arcs.add(create(315,Coloring.getColor(3)));
        innerarcs.add(createsmaller(45,Coloring.getColor(0)));
        innerarcs.add(createsmaller(315,Coloring.getColor(1)));
        innerarcs.add(createsmaller(225,Coloring.getColor(2)));
        innerarcs.add(createsmaller(135,Coloring.getColor(3)));

    }
    public Arc create(double start, Color color)
    {

        Arc arc=new Arc(500-(size+10),getHeight(),size,size,start,90);    //start=starting degree
        arc.setStroke(color);
        arc.setStrokeWidth(15);
        arc.setFill(Color.TRANSPARENT);
        arc.getTransforms().add(rotate);
        return arc;
    }
    public Arc createsmaller(double start, Color color)
    {

        Arc arc=new Arc(500+(size-35+10),getHeight(),size-35,size-35,start,90);    //start=starting degree
        arc.setStroke(color);
        arc.setStrokeWidth(15);
        arc.setFill(Color.TRANSPARENT);
        arc.getTransforms().add(rotate2);
        return arc;
    }
    @Override
    public void move()
    {
        currentAngle+=speed*0.75;
        rotate.setAngle(currentAngle);
        currentAngle2-=speed*0.75;
        rotate2.setAngle(currentAngle2);
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }
    public ArrayList<Arc> getInnerarcs() { return innerarcs; }

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
            if (Shape.intersect(ball, innerarcs.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i)))
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
            arc.setTranslateY(arc.getTranslateY()-y);
        }
        for(Arc arc:innerarcs)
        {
            arc.setTranslateY(arc.getTranslateY()-y);
        }
    }
}