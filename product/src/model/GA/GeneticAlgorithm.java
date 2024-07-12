package model.GA;

import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;

public class GeneticAlgorithm {
    private Configuration configuration;
    private Genotype population;

    public GeneticAlgorithm(Configuration conf) throws InvalidConfigurationException {
        this.configuration = conf;
        this.population = Genotype.randomInitialGenotype(conf);
    }

    public void evolve(int numberOfGenerations) {
        for (int i = 0; i < numberOfGenerations; i++) {
            population.evolve();
        }
    }

    public IChromosome getFittestChromosome() {
        return population.getFittestChromosome();
    }

    // Additional methods to save performance data and other operations can be added here
    public void savePerformanceData(String filePath, int numberOfGenerations) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("Generation,BestFitness,AverageFitness,WorstFitness\n");
            for (int i = 0; i < numberOfGenerations; i++) {
                population.evolve();

                IChromosome fittest = population.getFittestChromosome();
                double bestFitness = fittest.getFitnessValue();
                double averageFitness = calculateAverageFitness();
                double worstFitness = calculateWorstFitness();

                writer.write(String.format("%d,%f,%f,%f%n", i, bestFitness, averageFitness, worstFitness));
            }
        }
    }

    private double calculateAverageFitness() {
        double total = 0;
        for (Object chromosome : population.getPopulation().getChromosomes()) {
            total += ((IChromosome) chromosome).getFitnessValue();
        }
        return total / population.getPopulation().size();
    }

    private double calculateWorstFitness() {
        double worst = Double.MAX_VALUE;
        for (Object chromosome : population.getPopulation().getChromosomes()) {
            double fitness = ((IChromosome) chromosome).getFitnessValue();
            if (fitness < worst) {
                worst = fitness;
            }
        }
        return worst;
    }

    public void integrateNewcomer(IChromosome newcomer) {
        Population pop = population.getPopulation();
        // Ensure population size does not exceed the configured size
        if(pop.size() < configuration.getPopulationSize()){
            pop.addChromosome(newcomer);
            return;
        }
        // Assuming you want to replace the worst individual
        int worstIndex = findWorstChromosomeIndex(pop);
        pop.setChromosome(worstIndex, newcomer);
    }

    private int findWorstChromosomeIndex(Population pop) {
        double worstFitness = Double.MAX_VALUE;
        int worstIndex = -1;
        for (int i = 0; i < pop.size(); i++) {
            IChromosome chrom = pop.getChromosome(i);
            if (chrom.getFitnessValue() < worstFitness) {
                worstFitness = chrom.getFitnessValue();
                worstIndex = i;
            }
        }
        return worstIndex;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Object getPopulation() {
        return population.getPopulation();
    }

    // get population object
    public Population getPopulationObject() {
        return population.getPopulation();
    }
}
