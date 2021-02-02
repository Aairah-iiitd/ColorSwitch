package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class Super extends ImageView implements Collectible{
    private double height;
    public Super(double height)
    {
        Image image;
        try {
            image = new Image(new FileInputStream("src\\sample\\Image\\diamond.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        this.height=height;
        setImage(image);
        setX(480);
        setY(height-20);
        setFitHeight(40);
        setFitWidth(40);
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean isCollision(Ball ball)
    {
        return ball.getBoundsInParent().intersects(this.getBoundsInParent());
    }
}
