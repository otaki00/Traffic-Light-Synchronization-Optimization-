import java.util.Arrays;
import org.jgap.*;
import org.jgap.impl.*;

import model.IntersectionGene;
import model.MyFitnessFunction;

public class App {
    public static void main(String[] args) {
        try {
            Configuration conf = new DefaultConfiguration();

            // Set the fitness function with proper weighting
            FitnessFunction myFitnessFunction = new MyFitnessFunction(0.2, 0.1, 0.1);
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

            MutationOperator mutationOperator = new MutationOperator(conf, 1);
            conf.addGeneticOperator(mutationOperator);

            // Initialize the genetic algorithm and evolve the population
            Genotype population = Genotype.randomInitialGenotype(conf);
            evolvePopulation(population, 100); // Refactor evolution logic into a separate method for clarity

            // Retrieve and display the best solution
            IChromosome bestSolution = population.getFittestChromosome();
            System.out.println("Best solution: " + bestSolution.toString());
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void evolvePopulation(Genotype population, int numberOfGenerations) {
        for (int generation = 0; generation < numberOfGenerations; generation++) {
            population.evolve();

            // Optional: Add logic to periodically save population or output detailed stats
            // for analysis
            printGenerationDetails(population, generation);
            if (generation % 10 == 0 || generation == numberOfGenerations - 1) {
            }
        }
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
