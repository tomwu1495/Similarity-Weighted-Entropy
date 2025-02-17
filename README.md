Sequence Diversity Analysis Tools
This repository contains a set of Java functions designed for the analysis of genetic diversity in viral quasispecies. The key functions included in this program are:

Functions Overview
SimilarityMatrixGenerator
Description: This function generates similarity matrices for sequence populations. Each element 𝑆𝑖𝑗 in the matrix represents the genetic similarity between sequence and sequence 𝑗 in the population. The similarity is calculated based on a defined similarity metric (such as sequence identity or another distance measure).
Output: A matrix of genetic similarities between all pairs of sequences in the population.

EntropyCalculator
Description: This function calculates the entropy values for viral quasispecies diversity using four different entropy metrics:
H: Traditional Shannon entropy, representing overall sequence diversity.
Hn: Normalized Shannon entropy, scaled to range between 0 and 1.
Hsim: Similarity-weighted entropy, which incorporates sequence similarity into the entropy calculation.
Hnsim: Normalized similarity-weighted entropy, scaling the similarity-weighted entropy between 0 and 1.
Output: The computed values for each entropy metric.

SequenceGeneratorN
Description: This function generates populations of sequences with saturated mutations. Sequences are generated with lengths ranging from a single nucleotide (N) up to seven nucleotides (7Ns). The function simulates mutations across different sequence lengths, facilitating the analysis of genetic diversity at varying levels of complexity.
Output: Populations of mutated sequences at different lengths.

HammingDistanceMatrixCalculator
Description: This function computes a Hamming distance matrix for sequence populations, where the Hamming distance measures the number of positions at which two sequences differ. It also calculates the average Hamming distance across the entire population.
Output: A matrix representing the pairwise Hamming distances between sequences and the average Hamming distance for the population.
