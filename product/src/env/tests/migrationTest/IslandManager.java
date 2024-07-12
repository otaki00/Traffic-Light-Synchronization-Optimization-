package env.tests.migrationTest;


import java.util.ArrayList;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.*;
import org.jgap.impl.*;

import model.Fitnessfunction.MyFitnessFunction;
import model.GA.GeneticAlgorithm;
import model.Gene.IntersectionGene;
import model.Gene.custom.CustomMutationOperator;

public class IslandManager {
    private List<Thread> islandThreads = new ArrayList<>();
    private List<Island> islands = new ArrayList<>();
    private int numberOfIslands;
    private int migrationRate;

    public IslandManager(int numberOfIslands) {
        this.numberOfIslands = numberOfIslands;
    }

    public IslandManager(int numberOfIslands, int migrationRate) {
        this.numberOfIslands = numberOfIslands;
        this.migrationRate = migrationRate;
    }

    public void initializeIslands() throws InvalidConfigurationException {
        for (int i = 0; i < numberOfIslands; i++) {
            Configuration.reset();
            Configuration conf = new DefaultConfiguration();
            FitnessFunction myFitnessFunction = new MyFitnessFunction(0.5, 0.3, 0.2);
            conf.setFitnessFunction(myFitnessFunction);
            conf.setPopulationSize(100);

            Gene[] intersectionGenes = new Gene[5];
            for (int j = 0; j < intersectionGenes.length; j++) {
                intersectionGenes[j] = new IntersectionGene(conf);
            }

            IChromosome sampleChromosome = new Chromosome(conf, intersectionGenes);
            conf.setSampleChromosome(sampleChromosome);

            CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 80);
            conf.addGeneticOperator(crossoverOperator);

            MutationOperator mutationOperator = new CustomMutationOperator(conf);
            conf.addGeneticOperator(mutationOperator);

            GeneticAlgorithm ga = new GeneticAlgorithm(conf);
            String logFilePath = "data/island_" + i + ".csv";
            int port = 5000 + i; // Example port assignment, adjust as needed
            Island island = new Island(ga, i, logFilePath, port, migrationRate);
            islands.add(island);
            Thread thread = new Thread(island);
            islandThreads.add(thread);
        }
    }

    public void initializeIslandsForTest() throws InvalidConfigurationException {
        for (int i = 0; i < numberOfIslands; i++) {
            Configuration.reset();
            Configuration conf = new DefaultConfiguration();
            FitnessFunction myFitnessFunction = new MyFitnessFunction(0.5, 0.3, 0.2);
            conf.setFitnessFunction(myFitnessFunction);
            conf.setPopulationSize(100);

            Gene[] intersectionGenes = new Gene[5];
            for (int j = 0; j < intersectionGenes.length; j++) {
                intersectionGenes[j] = new IntersectionGene(conf);
            }

            IChromosome sampleChromosome = new Chromosome(conf, intersectionGenes);
            conf.setSampleChromosome(sampleChromosome);

            CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 80);
            conf.addGeneticOperator(crossoverOperator);

            MutationOperator mutationOperator = new CustomMutationOperator(conf);
            conf.addGeneticOperator(mutationOperator);

            GeneticAlgorithm ga = new GeneticAlgorithm(conf);
            String logFilePath = "data/island_" + i + ".csv";
            int port = 5000 + i; // Example port assignment, adjust as needed
            Island island = new Island(ga, i, logFilePath, port, migrationRate);
            islands.add(island);
            Thread thread = new Thread(island);
            islandThreads.add(thread);
        }
    }

    public void startIslands() {
        islandThreads.forEach(Thread::start);
    }

    public void joinIslands() {
        islandThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to join island thread: " + e.getMessage());
            }
        });
    }

    public double getBestOverallFitness() {
        return islands.stream()
                .mapToDouble(island -> island.getBestFitness())
                .max()
                .orElse(Double.MIN_VALUE);
    }
}
