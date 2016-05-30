import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the name of the file, either iris.data or nutrients.txt");
        System.out.println("Clusters will be drawn based on the first two columns of data");
        String filename = s.next();
        System.out.println("Enter k");
        int k = Integer.parseInt(s.next());
        System.out.println("What is the name of the output file?");
        String output = s.next();
        if(filename.equals("nutrients.txt"))
        {
            Graph g = new Graph("nutrients.txt", output, 1, 2, k);
        }
        if(filename.equals("iris.data"))
        {
            Graph g = new Graph("iris.data", output, 2, 3, k);
        }
    }
}
