
/**
 * 在这里给出对类 Hammingdistance 的描述。
 * 
 * @author (你的名字)
 * @version (一个版本号或者一个日期)
 */

import java.io.*;
import java.util.*;

public class HammingDistanceMatrixCalculator {

    // Method to calculate Hamming distance between two sequences
    public static int hammingDistance(String seq1, String seq2) {
        if (seq1.length() != seq2.length()) {
            throw new IllegalArgumentException("Sequences must have the same length.");
        }

        int distance = 0;
        for (int i = 0; i < seq1.length(); i++) {
            if (seq1.charAt(i) != seq2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    // Method to calculate the average Hamming distance for a population of sequences
    public static double calculateAverageHammingDistance(List<String> sequences) {
        int numSequences = sequences.size();
        int totalDistance = 0;
        int numPairs = 0;

        // Calculate pairwise Hamming distances
        for (int i = 0; i < numSequences; i++) {
            for (int j = i + 1; j < numSequences; j++) {
                totalDistance += hammingDistance(sequences.get(i), sequences.get(j));
                numPairs++;
            }
        }

        // Calculate average pairwise Hamming distance
        return numPairs == 0 ? 0 : (double) totalDistance / numPairs;
    }

    // Method to calculate and return the Hamming distance matrix
    public static int[][] calculateHammingDistanceMatrix(List<String> sequences) {
        int numSequences = sequences.size();
        int[][] matrix = new int[numSequences][numSequences];

        // Calculate pairwise Hamming distances and store in matrix
        for (int i = 0; i < numSequences; i++) {
            for (int j = 0; j < numSequences; j++) {
                if (i == j) {
                    matrix[i][j] = 0;  // Distance to itself is 0
                } else if (i < j) {
                    matrix[i][j] = hammingDistance(sequences.get(i), sequences.get(j));
                    matrix[j][i] = matrix[i][j]; // Symmetric matrix
                }
            }
        }
        return matrix;
    }

    // Method to read sequences from the FASTA file
    public static List<String> readSequencesFromFile(String filename) {
        List<String> sequences = new ArrayList<>();
        StringBuilder sequence = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Ignore lines starting with ">"
                if (line.startsWith(">")) {
                    // If there's a sequence already built, add it to the list
                    if (sequence.length() > 0) {
                        sequences.add(sequence.toString());
                        sequence.setLength(0); // Reset the sequence builder
                    }
                } else {
                    // Append sequence lines together
                    sequence.append(line.trim());
                }
            }
            // Add the last sequence if present
            if (sequence.length() > 0) {
                sequences.add(sequence.toString());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return sequences;
    }

    // Method to write the Hamming distance matrix to a CSV file
    public static void writeHammingDistanceMatrixToCSV(int[][] matrix, String outputFilename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) {
            int size = matrix.length;

            // Write header row
            bw.write("Sequence\\Sequence");
            for (int i = 1; i <= size; i++) {
                bw.write(",Seq" + i);
            }
            bw.newLine();

            // Write matrix rows
            for (int i = 0; i < size; i++) {
                bw.write("Seq" + (i + 1));
                for (int j = 0; j < size; j++) {
                    bw.write("," + matrix[i][j]);
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Read sequences from the file "1.txt"
        List<String> sequences = readSequencesFromFile("1.txt");

        // If the file is empty or has invalid sequences, print an error message and exit
        if (sequences.isEmpty()) {
            System.err.println("No sequences found in the file.");
            return;
        }

        // Calculate the average pairwise Hamming distance
        double averageDistance = calculateAverageHammingDistance(sequences);
        System.out.println("Average Pairwise Hamming Distance: " + averageDistance);

        // Calculate the Hamming distance matrix
        int[][] hammingMatrix = calculateHammingDistanceMatrix(sequences);

        // Write the Hamming distance matrix to a CSV file
        writeHammingDistanceMatrixToCSV(hammingMatrix, "hamming_distance_matrix.csv");
        System.out.println("Hamming distance matrix saved to hamming_distance_matrix.csv");
    }
}
