package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class DoubleCross extends Obstacle {
    private ArrayList<Line> lines=new ArrayList<>();
    private ArrayList<Line> rightlines=new ArrayList<>();
    private Rotate rotate;
    private Rotate rotate2;
    private double currentAngle2;
    private double x[];
    private double y[];

    public DoubleCross(double height,double size,double speed,double rot)
    {
        rotateType=true;
        setHeight(height);
        setSpeed(speed);
        rotate =new Rotate(0,400,height);
        rotate2 =new Rotate(0,600,height);
        type = "doubleCross";
        star = null;
        setSwitcher(new Switcher(height-200));
        power = null;
        superstar = null;
        currentAngle =rot;
        currentAngle2=-rot;
        double m=Math.sqrt(3);
        x=new double[]{400,400+size,400-size, 600, 600+size, 600 - size};
        y=new double[]{height,height-size,height+size};

        lines.add(create(x[0],y[0],x[1],y[0],Coloring.getColor(0), 1));
        lines.add(create(x[0],y[0],x[2],y[0],Coloring.getColor(1), 1));
        lines.add(create(x[0],y[0],x[0],y[1],Coloring.getColor(2), 1));
        lines.add(create(x[0],y[0],x[0],y[2],Coloring.getColor(3), 1));
        rightlines.add(create(x[3],y[0],x[5],y[0],Coloring.getColor(0),0));
        rightlines.add(create(x[3],y[0],x[4],y[0],Coloring.getColor(1),0));
        rightlines.add(create(x[3],y[0],x[3],y[1],Coloring.getColor(2),0));
        rightlines.add(create(x[3],y[0],x[3],y[2],Coloring.getColor(3), 0));
    }

    public Line create(double x1,double y1,double x2,double y2, Color color, int type)
    {

        Line line=new Line(x1,y1,x2,y2);
        line.setStroke(color);
        line.setStrokeWidth(20);
        line.setFill(Color.TRANSPARENT);
        if (type == 1)
            line.getTransforms().add(rotate);
        else
            line.getTransforms().add(rotate2);
        return line;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
    public ArrayList<Line> getRightLines() {
        return rightlines;
    }

    @Override
    public void move()
    {
        currentAngle+=speed;
        rotate.setAngle(currentAngle);
        currentAngle2-=speed;
        rotate2.setAngle(currentAngle2);
    }


    @Override
    public boolean isCollision(Ball ball)
    {
        boolean b=false;
        for(int i=0;i<lines.size();i++)
        {
            if (Shape.intersect(ball, lines.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i)))
            {
                b=true;
                break;
            }
            if (Shape.intersect(ball, rightlines.get(i)).getBoundsInLocal().getWidth() != -1 && !ball.getFill().equals(Coloring.getColor(i)))
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
        for(Line line:lines)
        {
            line.setTranslateY(line.getTranslateY()-y);
        }
        for(Line line:rightlines)
        {
            line.setTranslateY(line.getTranslateY()-y);
        }
    }

}

