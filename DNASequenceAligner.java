import java.util.Scanner;

public class DNASequenceAligner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first DNA sequence:");
        String seq1 = scanner.nextLine().toUpperCase();
        System.out.println("Enter second DNA sequence:");
        String seq2 = scanner.nextLine().toUpperCase();
        System.out.println("Got sequences: " + seq1 + " and " + seq2);
        scanner.close();
    }
}