import java.util.ArrayList;

/**
 * Created by Akrylic on 3/23/2016.
 */
public class Item {
    public static int number = 0;
    public Coordinate coordinate;
    public double closest = -1;
    public int color;
    public Item(double x, double y)
    {
        coordinate = new Coordinate(x, y);
        number++;
    }
}
