import java.util.Scanner;

public class DNASequenceAligner {
    private static final int MATCH_SCORE = 1;
    private static final int MISMATCH_SCORE = -1;
    private static final int GAP_PENALTY = -2;

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

        int score = alignSequences(seq1, seq2);
        System.out.println("Alignment score: " + score);
        scanner.close();
    }

    private static boolean isValidDNASequence(String seq) {
        return seq.matches("^[ATCG]+$");
    }

    private static int alignSequences(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] scoreMatrix = new int[m + 1][n + 1];

        // Initialize first row and column
        for (int i = 0; i <= m; i++) {
            scoreMatrix[i][0] = i * GAP_PENALTY;
        }
        for (int j = 0; j <= n; j++) {
            scoreMatrix[0][j] = j * GAP_PENALTY;
        }

        // Fill scoring matrix
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int match = scoreMatrix[i - 1][j - 1] +
                        (seq1.charAt(i - 1) == seq2.charAt(j - 1) ? MATCH_SCORE : MISMATCH_SCORE);
                int delete = scoreMatrix[i - 1][j] + GAP_PENALTY;
                int insert = scoreMatrix[i][j - 1] + GAP_PENALTY;
                scoreMatrix[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }

        return scoreMatrix[m][n]; // Just return score for now
    }
}