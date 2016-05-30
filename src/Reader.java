import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Akrylic on 3/23/2016.
 */
public class Reader
{
    public Scanner scanner;
    public List<String> data = new ArrayList();
    public int max;
    private String file;
    public Reader(String fileName) //constructor
    {
        file = fileName;
        fileRead();
        max= -1;
    }

    private void fileRead()
    {
        try {
            scanner = new Scanner(new File(file));
        }
        catch(Exception e){
            System.out.println("file not found");
        }
    }
    public boolean endOfFile;


    public List<String> nextLine() //returns a new transaction line
    {
        data = new ArrayList();
        try
        {
            String s = scanner.nextLine();
            Scanner stringScan = new Scanner(s);
            stringScan.useDelimiter(",");

            while (stringScan.hasNext()) {
                data.add((stringScan.next()));
            }
            if (!scanner.hasNextLine())
            {
                endOfFile=true;
                //System.out.println("end of file " +endOfFile);

            }

        }
        catch(NoSuchElementException e)
        {
            endOfFile=true;
            data.add(null);
            return data;
        }
        return data;
    }
}
