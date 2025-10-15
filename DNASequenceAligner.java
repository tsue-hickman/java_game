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

// Traceback to get actual aligned sequences - like finding the best path in a
// game!
private static ArrayList<String> getAlignedSequences(String seq1, String seq2, int[][] scoreMatrix) {
    ArrayList<String> aligned = new ArrayList<>(); // Using ArrayList from Collections!
    StringBuilder align1 = new StringBuilder();
    StringBuilder align2 = new StringBuilder();
    StringBuilder matches = new StringBuilder();

    int i = seq1.length();
    int j = seq2.length();

    // Go backwards through the matrix
    while (i > 0 || j > 0) {
        if (i > 0 && j > 0 &&
                seq1.charAt(i - 1) == seq2.charAt(j - 1) &&
                scoreMatrix[i][j] == scoreMatrix[i - 1][j - 1] + MATCH_SCORE) {
            // Match!
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