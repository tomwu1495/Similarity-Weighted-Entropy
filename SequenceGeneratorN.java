
/**
 * 在这里给出对类 n 的描述。
 * 
 * @author (你的名字)
 * @version (一个版本号或者一个日期)
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorN {

    private static final char[] BASES = {'A', 'T', 'G', 'C'};

    public static void main(String[] args) {
        int n = 2; // Adjust this value for the number of N's (e.g., 2 for NN, 3 for NNN, etc.)
        List<String> sequences = generateSequences(n);
        writeSequencesToCSV(sequences, "sequences.csv");
    }

    // Generates all possible sequences for NNN...N with a given length
    private static List<String> generateSequences(int n) {
        List<String> sequences = new ArrayList<>();
        generateSequencesHelper("", n, sequences);
        return sequences;
    }

    // Helper function to generate sequences recursively
    private static void generateSequencesHelper(String current, int n, List<String> sequences) {
        if (current.length() == n) {
            sequences.add(current);
            return;
        }
        for (char base : BASES) {
            generateSequencesHelper(current + base, n, sequences);
        }
    }

    // Writes the generated sequences to a CSV file in the required format
    private static void writeSequencesToCSV(List<String> sequences, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Seq,Sequence\n"); // Use comma to separate columns
            for (int i = 0; i < sequences.size(); i++) {
                writer.write("Seq " + (i + 1) + "," + sequences.get(i) + "\n"); // Use comma here as well
            }
            System.out.println("Sequences written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

