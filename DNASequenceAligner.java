import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class DNASequenceAligner {
    private static final int MATCH_SCORE = 1;
    private static final int MISMATCH_SCORE = -1;
    private static final int GAP_PENALTY = -2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String seq1 = "";
        String seq2 = "";

        // TRY TO READ FROM FILE FIRST - Stretch Challenge!
        try {
            File inputFile = new File("input.txt");
            if (inputFile.exists()) {
                System.out.println("Reading from input.txt...");
                Scanner fileScanner = new Scanner(inputFile);
                seq1 = fileScanner.nextLine().toUpperCase();
                seq2 = fileScanner.nextLine().toUpperCase();
                fileScanner.close();
            }
        } catch (Exception e) {
            System.out.println("File read failed, using manual input.");
        }

        // IF NO FILE, GET MANUAL INPUT
        if (seq1.isEmpty() || seq2.isEmpty()) {
            System.out.println("Enter first DNA sequence:");
            seq1 = scanner.nextLine().toUpperCase();
            System.out.println("Enter second DNA sequence:");
            seq2 = scanner.nextLine().toUpperCase();
        }

        // VALIDATE DNA SEQUENCES (only A,T,C,G allowed)
        if (!isValidDNASequence(seq1) || !isValidDNASequence(seq2)) {
            System.out.println("Error: Sequences can only have A, T, C, G!");
            scanner.close();
            return;
        }

        // CALCULATE ALIGNMENT SCORE
        int score = alignSequences(seq1, seq2);
        System.out.println("Alignment score: " + score);

        // Get and print aligned sequences using ArrayList!
        ArrayList<String> aligned = getAlignedSequences(seq1, seq2, getScoreMatrix(seq1, seq2));
        System.out.println("\nAligned sequences:");
        for (int k = 0; k < aligned.size(); k++) {
            System.out.println(aligned.get(k));
        }

        // WRITE TO OUTPUT FILE - Stretch Challenge!
        writeToFile(score, aligned);

        scanner.close();
    }

    // CHECK IF DNA SEQUENCE IS VALID
    private static boolean isValidDNASequence(String seq) {
        return seq.matches("^[ATCG]+$");
    }

    // SIMPLE VERSION - JUST RETURNS SCORE
    private static int alignSequences(String seq1, String seq2) {
        return getScoreMatrix(seq1, seq2)[seq1.length()][seq2.length()];
    }

    // HELPER TO BUILD SCORING MATRIX (Main algorithm!)
    private static int[][] getScoreMatrix(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] scoreMatrix = new int[m + 1][n + 1];

        // Initialize first row and column with gap penalties
        for (int i = 0; i <= m; i++) {
            scoreMatrix[i][0] = i * GAP_PENALTY;
        }
        for (int j = 0; j <= n; j++) {
            scoreMatrix[0][j] = j * GAP_PENALTY;
        }

        // Fill scoring matrix - this is the main algorithm loop!
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int match = scoreMatrix[i - 1][j - 1] +
                        (seq1.charAt(i - 1) == seq2.charAt(j - 1) ? MATCH_SCORE : MISMATCH_SCORE);
                int delete = scoreMatrix[i - 1][j] + GAP_PENALTY;
                int insert = scoreMatrix[i][j - 1] + GAP_PENALTY;
                scoreMatrix[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }
        return scoreMatrix;
    }

    // TRACEBACK TO GET ACTUAL ALIGNED SEQUENCES - like finding best path in a game!
    private static ArrayList<String> getAlignedSequences(String seq1, String seq2, int[][] scoreMatrix) {
        ArrayList<String> aligned = new ArrayList<>(); // Using ArrayList from Collections Framework!
        StringBuilder align1 = new StringBuilder();
        StringBuilder align2 = new StringBuilder();
        StringBuilder matches = new StringBuilder();

        int i = seq1.length();
        int j = seq2.length();

        // Go backwards through the matrix (traceback)
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 &&
                    seq1.charAt(i - 1) == seq2.charAt(j - 1) &&
                    scoreMatrix[i][j] == scoreMatrix[i - 1][j - 1] + MATCH_SCORE) {
                // Perfect match!
                align1.append(seq1.charAt(i - 1));
                align2.append(seq2.charAt(j - 1));
                matches.append('|');
                i--;
                j--;
            } else if (i > 0 && j > 0 &&
                    scoreMatrix[i][j] == scoreMatrix[i - 1][j - 1] + MISMATCH_SCORE) {
                // Mismatch
                align1.append(seq1.charAt(i - 1));
                align2.append(seq2.charAt(j - 1));
                matches.append('X');
                i--;
                j--;
            } else if (i > 0 && scoreMatrix[i][j] == scoreMatrix[i - 1][j] + GAP_PENALTY) {
                // Delete (gap in seq2)
                align1.append(seq1.charAt(i - 1));
                align2.append('-');
                matches.append(' ');
                i--;
            } else {
                // Insert (gap in seq1)
                align1.append('-');
                align2.append(seq2.charAt(j - 1));
                matches.append(' ');
                j--;
            }
        }

        // Reverse because we built backwards
        aligned.add(align1.reverse().toString());
        aligned.add(matches.reverse().toString());
        aligned.add(align2.reverse().toString());

        return aligned;
    }

    // WRITE RESULTS TO FILE - STRETCH CHALLENGE!
    private static void writeToFile(int score, ArrayList<String> aligned) {
        try {
            PrintWriter writer = new PrintWriter("output.txt");
            writer.println("DNA Sequence Alignment Results");
            writer.println("================================");
            writer.println("Score: " + score);
            writer.println();
            writer.println("Aligned Sequences:");
            for (String line : aligned) {
                writer.println(line);
            }
            writer.close();
            System.out.println("\nResults saved to output.txt!");
        } catch (Exception e) {
            System.out.println("File write failed.");
        }
    }
}