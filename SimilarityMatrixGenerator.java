
/**
* Write a description of class SimilarityMatrixGenerator here.
*
* @author : Jian Wu （吴健）
* @version: version 1, 2025
* License: MIT
 */


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimilarityMatrixGenerator {
    public static void main(String[] args) {
        String inputFile = "x.csv"; // Input file with sequences
        String outputFile = "similarity_matrix.csv"; // Output file for similarity matrix

        // Read sequences from the input file
        List<String> sequences = readSequences(inputFile);
        
        // Calculate similarity matrix
        double[][] similarityMatrix = calculateSimilarityMatrix(sequences);
        
        // Write similarity matrix to the output file
        writeMatrixToFile(similarityMatrix, outputFile);
        
        System.out.println("Similarity matrix generated and saved to " + outputFile);
    }

    // Function to read sequences from the CSV file
    private static List<String> readSequences(String filePath) {
        List<String> sequences = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split by comma (adjust if needed)
                if (parts.length > 1) {
                    sequences.add(parts[1].trim()); // Add the sequence part (second column)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sequences;
    }

    // Function to calculate the similarity matrix
    private static double[][] calculateSimilarityMatrix(List<String> sequences) {
        int n = sequences.size();
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matrix[i][j] = 1.0; // Similarity with itself is 1
                } else {
                    matrix[i][j] = calculateSimilarity(sequences.get(i), sequences.get(j));
                }
            }
        }
        return matrix;
    }

    // Function to calculate similarity between two sequences
    private static double calculateSimilarity(String seq1, String seq2) {
        int matches = 0;
        int length = seq1.length();

        for (int i = 0; i < length; i++) {
            if (seq1.charAt(i) == seq2.charAt(i)) {
                matches++;
            }
        }
        return (double) matches / length;
    }

    // Function to write the matrix to a CSV file
    private static void writeMatrixToFile(double[][] matrix, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            int n = matrix.length;
            
            // Write header
            bw.write(",");
            for (int i = 1; i <= n; i++) {
                bw.write("Seq " + i);
                if (i < n) bw.write(",");
            }
            bw.newLine();

            // Write matrix rows
            for (int i = 0; i < n; i++) {
                bw.write("Seq " + (i + 1) + ",");
                for (int j = 0; j < n; j++) {
                    bw.write(String.format("%.2f", matrix[i][j]));
                    if (j < n - 1) bw.write(",");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
