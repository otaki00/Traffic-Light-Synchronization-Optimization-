import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

import org.jgap.*;
import org.jgap.impl.*;

import model.Fitnessfunction.MyFitnessFunction;
import model.Fitnessfunction.PopulationOperations;
import model.GA.GeneticAlgorithm;
import model.Gene.IntersectionGene;
import model.Gene.custom.CustomMutationOperator;
import model.Island.Island;
import model.Island.IslandManager;

public class App {
    public static void main(String[] args) {
        try {
            int numberOfIslands = 4;
            IslandManager manager = new IslandManager(numberOfIslands);
            manager.initializeIslands();

            CyclicBarrier barrier = new CyclicBarrier(numberOfIslands,
                    () -> System.out.println("All islands have reached the exchange point."));
            Island.setBarrier(barrier);

            manager.startIslands();
            manager.joinIslands();
        } catch (InvalidConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
    }

    private static void evolvePopulation(Genotype population, int numberOfGenerations) throws IOException {

        // add logic for save the outcomes data from each generation, in extarnal file, to apply tests
        FileWriter writer = new FileWriter("ga_performance.csv", false); // Append mode
        writer.write("Generation,BestFitness,AverageFitness,WorstFitness\n"); 


        for (int generation = 0; generation < numberOfGenerations; generation++) {
            population.evolve();



            // Optional: Add logic to periodically save population or output detailed stats
            // for analysis
            printGenerationDetails(population, generation);
            
            
            IChromosome fittestChromosome = population.getFittestChromosome();
            double bestFitness = fittestChromosome.getFitnessValue();
            double averageFitness = PopulationOperations.calculateAverageFitness(population.getPopulation());
            double worstFitness = PopulationOperations.calculateWorstFitness(population.getPopulation());

            // Log the data to the CSV file
            writer.write(generation + "," + bestFitness + "," + averageFitness + "," + worstFitness + "\n");


        }

        writer.close();
    }

    private static void printGenerationDetails(Genotype population, int generation) {
        IChromosome fittestChromosome = population.getFittestChromosome();
        // double totalFitness = population.getPopulation().getChromosomes().stream().mapToDouble(Gene::getFitnessValue)
        //         .sum();
        // double averageFitness = totalFitness / population.getPopulation().size();

        System.out.println("Generation: " + generation);
        System.out.println("Best Fitness: " + fittestChromosome.getFitnessValue());
        // System.out.println("Average Fitness: " + averageFitness);

        System.out.print("Best Chromosome: [");
        for (int i = 0; i < fittestChromosome.size(); i++) {
            int[] geneValues = (int[]) fittestChromosome.getGene(i).getAllele();
            System.out.print(Arrays.toString(geneValues) + (i < fittestChromosome.size() - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}
