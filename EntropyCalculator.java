/**
* The EntropyCalculator Java program calculates various entropy values (H, Hn, Hsim, Hnsim) for a given sequence population based on their frequencies and sequence similarities. The program reads the data from two CSV files: one for sequence frequencies and one for similarity matrices. The entropy calculations are performed using the following methods:
H: Basic Shannon entropy, calculated as -∑fi * log2(fi).
Hn: Normalized Shannon entropy, adjusted by dividing H by log2(N).
Hsim: Similarity-weighted entropy, incorporating sequence similarities.
Hnsim: Normalized similarity-weighted entropy, similar to Hn but accounting for sequence similarities.
The program uses the BufferedReader to load the CSV data, and then computes the entropies with functions that handle frequency and similarity data. Finally, the results are printed in the console.
* @author : Jian Wu （吴健）
* @version: version 1, 2025
* License: MIT
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntropyCalculator {
    public static void main(String[] args) {
        // File paths for the CSV files
        String frequenciesFile = "1.csv";
        String similaritiesFile = "2.csv";
        
        // Read data from CSV files
        List<Double> frequencies = readFrequencies(frequenciesFile);
        double[][] similarities = readSimilarities(similaritiesFile);
        
        int N = frequencies.size();
        
        // Calculate entropy values
        double H = calculateH(frequencies);
        double Hn = calculateHn(frequencies, N);
        double Hsim = calculateHsim(frequencies, similarities);
        double Hnsim = calculateHnsim(frequencies, similarities, N);
        
        // Print the results
        System.out.printf("H = %.5f\n", H);
        System.out.printf("Hn = %.5f\n", Hn);
        System.out.printf("Hsim = %.5f\n", Hsim);
        System.out.printf("Hnsim = %.5f\n", Hnsim);
    }
    
    // Function to read frequencies from CSV
    private static List<Double> readFrequencies(String filePath) {
        List<Double> frequencies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header row
            if (line != null) line = br.readLine(); // Read the actual data row
            if (line != null) {
                String[] values = line.split(",");
                for (String value : values) {
                    frequencies.add(Double.parseDouble(value.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequencies;
    }

    // Function to read similarity matrix from CSV
    private static double[][] readSimilarities(String filePath) {
        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] row = new double[values.length - 1];
                for (int i = 1; i < values.length; i++) { // Skip first column
                    row[i - 1] = Double.parseDouble(values[i].trim());
                }
                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows.toArray(new double[0][]);
    }

    // Function to calculate H = -∑i fi * log2(fi)
    private static double calculateH(List<Double> frequencies) {
        double H = 0.0;
        for (double fi : frequencies) {
            H -= fi * log2(fi);
        }
        return H;
    }

    // Function to calculate Hn = -∑i fi * log2(fi) / log2(N)
    private static double calculateHn(List<Double> frequencies, int N) {
        double H = calculateH(frequencies);
        return H / log2(N);
    }

    // Function to calculate Hsim = -∑i fi * log2(fi) * (1 - Smean, weighted (i))
    private static double calculateHsim(List<Double> frequencies, double[][] similarities) {
        double Hsim = 0.0;
        int N = frequencies.size();
        
        for (int i = 0; i < N; i++) {
            double fi = frequencies.get(i);
            double SmeanWeighted = calculateSmeanWeighted(i, frequencies, similarities);
            Hsim -= fi * log2(fi) * (1 - SmeanWeighted);
        }
        return Hsim;
    }

    // Function to calculate Hnsim = -∑i fi * log2(fi) * (1 - Smean, weighted (i)) / log2(N)
    private static double calculateHnsim(List<Double> frequencies, double[][] similarities, int N) {
        double Hsim = calculateHsim(frequencies, similarities);
        return Hsim / log2(N);
    }

    // Function to calculate Smean, weighted(i) = ∑j≠i fj * Sij
    private static double calculateSmeanWeighted(int i, List<Double> frequencies, double[][] similarities) {
        double SmeanWeighted = 0.0;
        int N = frequencies.size();
        
        for (int j = 0; j < N; j++) {
            if (i != j) {
                SmeanWeighted += frequencies.get(j) * similarities[i][j];
            }
        }
        return SmeanWeighted;
    }

    // Utility function to calculate log2
    private static double log2(double value) {
        return Math.log(value) / Math.log(2);
    }
}
