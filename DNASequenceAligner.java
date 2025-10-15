import java.util.Scanner;

public class DNASequenceAligner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first DNA sequence:");
        String seq1 = scanner.nextLine().toUpperCase();
        System.out.println("Enter second DNA sequence:");
        String seq2 = scanner.nextLine().toUpperCase();

        if (!isValidDNASequence(seq1) || !isValidDNASequence(seq2)) {
            System.out.println("Error: Sequences can only have A, T, C, G!");
            scanner.close();
            return;
        }

        System.out.println("Got valid sequences: " + seq1 + " and " + seq2);
        scanner.close();
    }

    private static boolean isValidDNASequence(String seq) {
        return seq.matches("^[ATCG]+$");
    }
}