import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Akrylic on 3/23/2016.
 */
public class Centroid
{
    public static int k=0;
    public Coordinate coordinate;
    public static ArrayList<Integer> colors = new ArrayList<>();
    public int color;
    public Centroid(double xMax, double yMax, double xMin, double yMin)
    {
        Random r = new Random();
        double xRandom = xMin + (xMax- xMin) * r.nextDouble();
        //r = new Random();
        Random d = new Random();
        //System.out.println(yMin +"ymin "+ yMax+" yMax");
        double yRandom = yMin + (yMax- yMin) * d.nextDouble();
        System.out.println(yRandom);
        coordinate = new Coordinate(xRandom, yRandom);
        colors.add(k);
        color = k;
        k++;
    }

}
