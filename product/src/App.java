import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import org.jgap.*;
import org.jgap.impl.*;

import model.IntersectionGene;
import model.MyFitnessFunction;
import model.PopulationOperations;
import model.custom.CustomMutationOperator;

public class App {
    public static void main(String[] args) throws IOException {
        try {
            Configuration conf = new DefaultConfiguration();

            // Set the fitness function with proper weighting
            // the sum of Ws must be 100
            FitnessFunction myFitnessFunction = new MyFitnessFunction(0.5, 0.3, 0.2);
            conf.setFitnessFunction(myFitnessFunction);

            // Correctly initialize and set up the sample chromosome
            Gene[] intersectionGenes = new Gene[5]; // Use Gene[] to align with JGAP's expectations

            for (int i = 0; i < intersectionGenes.length; i++) {
                intersectionGenes[i] = new IntersectionGene(conf);
                // It's assumed your IntersectionGene properly implements the random value
                // setting in its constructor or elsewhere
            }

            IChromosome sampleChromosome = new Chromosome(conf, intersectionGenes);
            conf.setSampleChromosome(sampleChromosome);

            // Setting up the population size
            conf.setPopulationSize(100);

            // Adjust crossover and mutation rates as needed
            CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 85);
            conf.addGeneticOperator(crossoverOperator);

            MutationOperator mutationOperator = new CustomMutationOperator(conf);
            conf.addGeneticOperator(mutationOperator);

            // Initialize the genetic algorithm and evolve the population
            Genotype population = Genotype.randomInitialGenotype(conf);
            evolvePopulation(population, 100);

            // Retrieve and display the best solution
            IChromosome bestSolution = population.getFittestChromosome();
            for (int i = 0; i < bestSolution.size(); i++) {
                int[] geneValues = (int[]) bestSolution.getGene(i).getAllele();
                System.out.println("Best Chromosome: " + Arrays.toString(geneValues));
            }
            
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
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
