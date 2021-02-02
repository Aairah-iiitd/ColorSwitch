package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Cross extends Obstacle {
    private ArrayList<Line> lines=new ArrayList<>();
    private Rotate rotate;
    private double x[];
    private double y[];

    public Cross(double height,double size,double speed,double rot)
    {
        setHeight(height);
        setSpeed(speed);
        rotate =new Rotate(0,400,height);
        rotateType=true;
        type = "cross";
        star = null;
        setSwitcher(new Switcher(height-200));
        power = null;
        superstar = null;
        currentAngle =rot;
        double m=Math.sqrt(3);
        x=new double[]{400,400+size,400-size};
        y=new double[]{height,height-size,height+size};

        lines.add(create(x[0],y[0],x[1],y[0],Coloring.getColor(0)));
        lines.add(create(x[0],y[0],x[2],y[0],Coloring.getColor(1)));
        lines.add(create(x[0],y[0],x[0],y[1],Coloring.getColor(2)));
        lines.add(create(x[0],y[0],x[0],y[2],Coloring.getColor(3)));
    }

    public Line create(double x1,double y1,double x2,double y2, Color color)
    {

        Line line=new Line(x1,y1,x2,y2);
        line.setStroke(color);
        line.setStrokeWidth(20);
        line.setFill(Color.TRANSPARENT);
        line.getTransforms().add(rotate);
        return line;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    @Override
    public void move()
    {
        currentAngle+=speed;
        rotate.setAngle(currentAngle);
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

        }
        return b;

    }

    public void scroll(double y)
    {
        super.scroll(y);
        for(Line line:lines)
        {
            line.setLayoutY(line.getLayoutY()-y);
        }

    }

}
