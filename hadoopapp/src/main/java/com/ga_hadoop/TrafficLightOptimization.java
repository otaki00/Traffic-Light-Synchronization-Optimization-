package com.ga_hadoop;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Collections;
import java.util.List;

import org.apache.spark.SparkConf;
import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;

import com.ga_hadoop.Fitnessfunction.MyFitnessFunction;
import com.ga_hadoop.GA.GeneticAlgorithm;
import com.ga_hadoop.Gene.IntersectionGene;



public class TrafficLightOptimization {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Traffic Light Optimization GA");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Assuming you have your traffic data as CSV files
        String dataPath = "C:\\Users\\asus\\Desktop\\term2_2024\\gradutation_project\\Traffic-Light-Synchronization-Optimization-\\data.csv"; // Update this path
        JavaRDD<String> data = sc.textFile(dataPath);

        // Distribute the GA execution
        JavaRDD<Double> fitnessScores = data.mapPartitions(lines -> {
            Configuration gaConf = new DefaultConfiguration();
            gaConf.setFitnessFunction(new MyFitnessFunction(0.5, 0.3, 0.2));

            // Setup the chromosomes, genes, etc.
            Gene[] genes = new Gene[5]; // adjust size accordingly
            for (int i = 0; i < genes.length; i++) {
                genes[i] = new IntersectionGene(gaConf);
            }
            IChromosome sampleChromosome = new Chromosome(gaConf, genes);
            gaConf.setSampleChromosome(sampleChromosome);
            gaConf.setPopulationSize(50);

            GeneticAlgorithm ga = new GeneticAlgorithm(gaConf);
            ga.evolve(50); // Run GA evolution for a number of generations

            IChromosome bestSolution = ga.getFittestChromosome();
            return Collections.singleton(bestSolution.getFitnessValue()).iterator();
        });

        // Collect and process results
        List<Double> results = fitnessScores.collect();
        results.forEach(System.out::println);

        sc.close();
    }
}
