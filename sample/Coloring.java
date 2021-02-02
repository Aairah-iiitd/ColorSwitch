package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Coloring {
    private static ArrayList<Color> colors =new ArrayList<Color>(Arrays.asList(Color.YELLOW,Color.PURPLE,Color.RED,Color.CYAN));
    public static Color getColor(int i)
    {
        return colors.get(i);
    }
}
