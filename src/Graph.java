import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Akrylic on 3/23/2016.
 */
public class Graph
{
    public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<Centroid> centroids = new ArrayList<>();
    private double yMin;
    private double yMax;
    private double xMin;
    private double xMax;
    private boolean redo = true;

    public Graph(String filename, String fileOut,int xAxis, int yAxis, int k) //xAxis and yAxis are
    {
        Reader r = new Reader(filename);
        while(!r.endOfFile)  //Load Items
        {
            try
            {
                List<String> list = r.nextLine();
                //System.out.println(list.get(xAxis) + " " + list.get(yAxis));
                Item i = new Item(Double.parseDouble(list.get(xAxis)), Double.parseDouble(list.get(yAxis)));
                items.add(i);
            }
            catch (java.lang.IndexOutOfBoundsException e)
            {
                break;
            }
        }
        getX();
        getY();

        for(int i =0; i < k; i++)  //Load Items
        {
            centroids.add(new Centroid(xMax, yMax, xMin, yMin));
        }
        for(Centroid c: centroids)
        {
            System.out.println("Centroid: "+c.coordinate.x+" "+c.coordinate.y);
        }

        Color();
        int count = 0;
        while(redo)
        {
            System.out.println("Recentering");
            ReCenter();
            Color();
            count++;
            ReCenter();
            Color();

        }
        System.out.println(count);
        for(Item i: items)
        {
            System.out.println("Final Color: "+i.coordinate.x+" "+i.coordinate.y+" "+i.color);
        }
        double sse=0;
        double centroidSSE;
        double centroidMean;
        for(Centroid c : centroids)
        {
            centroidMean =0;
            centroidSSE =0;
            int colorCount = 1;
            for(Item i : items)
            {
                if(i.color==c.color) {
                    double d = (Math.sqrt(Math.pow(i.coordinate.x - c.coordinate.x, 2) + Math.pow(i.coordinate.y - c.coordinate.y, 2)));
                    centroidMean+= d;
                    colorCount++;
                }
            }
            centroidMean=centroidMean/colorCount;
            for(Item i : items)
            {
                if(i.color==c.color) {
                    double d = (Math.sqrt(Math.pow(i.coordinate.x - c.coordinate.x, 2) + Math.pow(i.coordinate.y - c.coordinate.y, 2)));
                    centroidSSE+= Math.pow(d-centroidMean,2);
                    System.out.println("d: "+d);
                    colorCount++;
                }
            }
            System.out.println("Centroid mean: "+centroidMean+" Centroid SSE: "+centroidSSE);
            sse+=centroidSSE;

        }
        System.out.println(sse);
        //WriteFile
        try
        {
            PrintWriter FileOut = new PrintWriter(fileOut);
            FileOut.println("SSE: "+sse);
            ArrayList<Integer> clusterCounts = new ArrayList<>();
            for(Centroid c : centroids)
            {
                clusterCounts.add(0);
            }
            //System.out.println(clusterCounts);
            for(Item y : items)
            {
                clusterCounts.set(y.color, clusterCounts.get(y.color) + 1);
            }
            for(int u = 0; u <clusterCounts.size(); u++)
            {
                FileOut.println("Cluster (" +u+") Count: "+clusterCounts.get(u));
            }
            for(Item i: items)
            {
                FileOut.println("Item coordinate pair: "+i.coordinate.x+" "+i.coordinate.y+" Assigned cluster: "+i.color);
            }
            //FileOut.println(f.pruneMap);
            //FileOut.println(f.pruneMaps);
            FileOut.close();
        }
        catch(Exception e)
        {
            System.out.println("file not found");
        }



    }
    private void ReCenter()
    {
        double avgX=0;
        double avgY=0;
        List<Coordinate> oldCentroids = new ArrayList<>();
        double oldX;
        double oldY;
        for (Centroid c: centroids)
        {
            int color = c.color;
            double count =0;
            System.out.println("color "+color);
            for (Item i : items)
            {
                //System.out.println("i.color "+i.color);
                if (i.color == color)
                {
                    count++;
                    avgX+=i.coordinate.x;
                    avgY+=i.coordinate.y;
                }
            }

            avgX = avgX/count;
            System.out.println("avgX "+avgX);
            avgY = avgX/count;
            //System.out.println("avgY " + avgY);
            oldX = c.coordinate.x;
            oldY = c.coordinate.y;
            oldCentroids.add(new Coordinate(oldX, oldY));
            c.coordinate.x=avgX;
            c.coordinate.y=avgY;
        }
        for (Centroid c: centroids)
        {
            //System.out.println(c.coordinate.x+" old centroid: "+oldCentroids.get(c.color).x);
            if(c.coordinate.x!=oldCentroids.get(c.color).x && c.coordinate.y!=oldCentroids.get(c.color).y)
            {
                redo = true;
                System.out.println("redo "+c.coordinate.x+" "+oldCentroids.get(c.color).x);
                break;
            }
            else {
                System.out.println("end redo");
                redo = false;
            }
        }
    }
    private void Color()
    {
        for (Item i : items)
        {
            for(Centroid c : centroids) //Euclidean Distance Calculator
            {
                double d = Math.sqrt(Math.pow(i.coordinate.x - c.coordinate.x, 2)+Math.pow(i.coordinate.y - c.coordinate.y, 2));
                //System.out.println(d+" and "+i.closest);
                if (d < i.closest || i.closest == -1)
                {
                    i.closest = d;
                    i.color = c.color;
                    System.out.println("colored "+i.coordinate.x+ " "+i.color);
                }
            }
        }
    }
    private void getX() //Get Max and min for x
    {
        xMin = items.get(0).coordinate.x;
        xMax = items.get(0).coordinate.x;
        for(Item i: items)
        {
            if(i.coordinate.x>xMax)
                xMax = i.coordinate.x;
            if (i.coordinate.x<xMin)
                xMin = i.coordinate.x;
        }
    }
    private void getY() //Get Max and min for y
    {
        yMin = items.get(0).coordinate.y;
        yMax = items.get(0).coordinate.y;
        for(Item i: items)
        {
            if(i.coordinate.y>yMax)
                yMax = i.coordinate.y;
            if (i.coordinate.y<yMin)
                yMin = i.coordinate.y;
        }
    }
}
