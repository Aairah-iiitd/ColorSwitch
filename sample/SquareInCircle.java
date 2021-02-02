package sample;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class SquareInCircle extends Obstacle {
    private ArrayList<Arc> arcs=new ArrayList<>();
    private ArrayList<Line> lines = new ArrayList<>();
    private Rotate rotate, rotate2;
    private double currentAngle2;
    private double size;
    private double x[];
    private double y[];
    public SquareInCircle(double height,double size,double speed,double rot)
    {
        rotateType=true;
        setHeight(height);
        setSpeed(speed);
        this.size=size;
        type = "squareInCircle";
        rotate =new Rotate(rot,500,height);
        rotate2 = new Rotate (-(rot+45),500,height);
        power = null;
        superstar = null;
        star = null;
        setSwitcher(new Switcher(height-200));
        currentAngle =rot;
        currentAngle2=-(rot+45);
        arcs.add(create(0,Coloring.getColor(0)));
        arcs.add(create(90,Coloring.getColor(1)));
        arcs.add(create(180,Coloring.getColor(2)));
        arcs.add(create(270,Coloring.getColor(3)));
        double m=Math.sqrt(3);
        x=new double[]{500-size,500+size};
        y=new double[]{height-size,height+size};
        lines.add(createsmaller(x[0],y[0],x[1],y[0],Coloring.getColor(0)));
        lines.add(createsmaller(x[1],y[1],x[1],y[0],Coloring.getColor(1)));
        lines.add(createsmaller(x[1],y[1],x[0],y[1],Coloring.getColor(2)));
        lines.add(createsmaller(x[0],y[0],x[0],y[1],Coloring.getColor(3)));
    }
    public Arc create(double start, Color color)
    {

        Arc arc=new Arc(500,getHeight(),size-20,size-20,start,90);    //start=starting degree
        arc.setStroke(color);
        arc.setStrokeWidth(20);
        arc.setFill(Color.TRANSPARENT);
        arc.getTransforms().add(rotate2);
        return arc;
    }
    public Line createsmaller(double x1,double y1,double x2,double y2, Color color)
    {

        Line line=new Line(x1,y1,x2,y2);
        line.setStroke(color);
        line.setStrokeWidth(20);
        line.setFill(Color.TRANSPARENT);
        line.getTransforms().add(rotate);
        return line;
    }
    @Override
    public void move()
    {
        currentAngle+=speed;
        rotate.setAngle(currentAngle);
        currentAngle2-=speed;
        rotate2.setAngle(currentAngle2);
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    public ArrayList<Line> getLines() {
        return lines;
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
            if (Shape.intersect(ball, lines.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i)))
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
        for(Line lines:lines)
        {
            lines.setTranslateY(lines.getTranslateY()-y);
        }
    }
}


