import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter; // FIXED IMPORT!

public class DNASequenceAligner {
    // ... [KEEP ALL METHODS SAME AS ABOVE UNTIL writeToFile] ...

    // STILL BROKEN - using Scanner to WRITE (wrong!)
    private static void writeToFile(int score, ArrayList<String> aligned) {
        try {
            Scanner writer = new Scanner("output.txt"); // WRONG! Scanner is for READING!
            writer.println("Score: " + score); // CRASHES HERE
            for (String line : aligned) {
                writer.println(line);
            }
            writer.close();
            System.out.println("Saved to file!");
        } catch (Exception e) {
            System.out.println("FILE ERROR: " + e.getMessage());
        }
    }
}