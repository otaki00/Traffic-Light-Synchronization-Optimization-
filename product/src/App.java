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
    
}
