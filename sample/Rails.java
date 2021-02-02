package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Rails extends Obstacle {
    private ArrayList<Line> upperLines=new ArrayList<>();
    private ArrayList<Line> lowerLines=new ArrayList<>();
    private double x[];
    private boolean dir;

    public Rails(double height,double size,double speed,double dist)
    {
        dir=true;
        rotateType=false;
        setHeight(height);
        setSpeed(speed);
        star = null;
        type = "rails";
        setSwitcher(new Switcher(height-200));
        power = null;
        superstar = null;
        double m=Math.sqrt(3);
        x=new double[24];
        for(int i=-8;i<16;i++)
            x[i+8]=(i*170+dist);
        for(int i=0;i<23;i++)
        {
            upperLines.add(create(x[i],height-80,x[i+1],height-80,Coloring.getColor(i%4)));
            lowerLines.add(create(x[i],height+80,x[i+1],height+80,Coloring.getColor((i+1)%4)));
        }

    }

    public Line create(double x1,double y1,double x2,double y2, Color color)
    {

        Line line=new Line(x1,y1,x2,y2);
        line.setStroke(color);
        line.setStrokeWidth(20);
        line.setFill(Color.TRANSPARENT);
        return line;
    }

    public ArrayList<Line> getUpperLines() {
        return upperLines;
    }

    public ArrayList<Line> getLowerLines() {
        return lowerLines;
    }

    @Override
    public void move()
    {
        double s;
        if(dir)
            s=2*speed;
        else
            s=-2*speed;
        distance+=s;
        if(distance>400)
            dir=false;
        else if(distance<-400)
            dir=true;
        for(int i=0;i<lowerLines.size();i++)
        {
            Line line=lowerLines.get(i);
            line.setStartX(line.getStartX()-s);
            line.setEndX(line.getEndX()-s);

        }
        for(int i=0;i<upperLines.size();i++)
        {
            Line line=upperLines.get(i);
            line.setStartX(line.getStartX()+s);
            line.setEndX(line.getEndX()+s);

        }
    }


    @Override
    public boolean isCollision(Ball ball)
    {
        boolean b=false;
        for(int i=0;i<upperLines.size();i++)
        {
            if (Shape.intersect(ball, upperLines.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i%4)))
            {
                b=true;
                break;
            }

        }
        for(int i=0;i<lowerLines.size();i++)
        {
            if (Shape.intersect(ball, lowerLines.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor((i+1)%4)))
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
        for(Line line:upperLines)
        {
            line.setLayoutY(line.getLayoutY()-y);
        }
        for(Line line:lowerLines)
        {
            line.setLayoutY(line.getLayoutY()-y);
        }

    }



}
