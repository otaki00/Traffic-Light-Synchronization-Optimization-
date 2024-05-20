package model.Island;

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

    public IslandManager(int numberOfIslands) {
        this.numberOfIslands = numberOfIslands;
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

            CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 85);
            conf.addGeneticOperator(crossoverOperator);

            MutationOperator mutationOperator = new CustomMutationOperator(conf);
            conf.addGeneticOperator(mutationOperator);

            GeneticAlgorithm ga = new GeneticAlgorithm(conf);
            String logFilePath = "data/island_" + i + ".csv";
            Island island = new Island(ga, i, logFilePath);
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
}
