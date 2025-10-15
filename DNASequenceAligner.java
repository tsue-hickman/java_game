import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.Scanner; // WRONG - forgot PrintWriter!

public class DNASequenceAligner {
    private static final int MATCH_SCORE = 1;
    private static final int MISMATCH_SCORE = -1;
    private static final int GAP_PENALTY = -2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String seq1 = "";
        String seq2 = "";

        // TRY TO READ FROM FILE FIRST
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

        if (seq1.isEmpty() || seq2.isEmpty()) {
            System.out.println("Enter first DNA sequence:");
            seq1 = scanner.nextLine().toUpperCase();
            System.out.println("Enter second DNA sequence:");
            seq2 = scanner.nextLine().toUpperCase();
        }

        if (!isValidDNASequence(seq1) || !isValidDNASequence(seq2)) {
            System.out.println("Error: Sequences can only have A, T, C, G!");
            scanner.close();
            return;
        }

        int score = alignSequences(seq1, seq2);
        System.out.println("Alignment score: " + score);

        ArrayList<String> aligned = getAlignedSequences(seq1, seq2, getScoreMatrix(seq1, seq2));
        System.out.println("\nAligned sequences:");
        for (int k = 0; k < aligned.size(); k++) {
            System.out.println(aligned.get(k));
        }

        // TRYING FILE WRITE BUT BROKE
        writeToFile(score, aligned);

        scanner.close();
    }

    private static boolean isValidDNASequence(String seq) {
        return seq.matches("^[ATCG]+$");
    }

    private static int alignSequences(String seq1, String seq2) {
        return getScoreMatrix(seq1, seq2)[seq1.length()][seq2.length()];
    }

    private static int[][] getScoreMatrix(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] scoreMatrix = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            scoreMatrix[i][0] = i * GAP_PENALTY;
        }
        for (int j = 0; j <= n; j++) {
            scoreMatrix[0][j] = j * GAP_PENALTY;
        }

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

    private static ArrayList<String> getAlignedSequences(String seq1, String seq2, int[][] scoreMatrix) {
        ArrayList<String> aligned = new ArrayList<>();
        StringBuilder align1 = new StringBuilder();
        StringBuilder align2 = new StringBuilder();
        StringBuilder matches = new StringBuilder();

        int i = seq1.length();
        int j = seq2.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 &&
                    seq1.charAt(i - 1) == seq2.charAt(j - 1) &&
                    scoreMatrix[i][j] == scoreMatrix[i - 1][j - 1] + MATCH_SCORE) {
                align1.append(seq1.charAt(i - 1));
                align2.append(seq2.charAt(j - 1));
                matches.append('|');
                i--; j--;
            } else if (i > 0 && j > 0 &&
                    scoreMatrix[i][j] == scoreMatrix[i - 1][j