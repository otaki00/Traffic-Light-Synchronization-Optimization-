package env.tests.migrationTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

import org.jgap.InvalidConfigurationException;



public class App {
    private static final int NUMBER_OF_RUNS = 1000;
    private static final int[] MIGRATION_RATES = { 3, 5, 10, 15, 30 };

    public static void main(String[] args) throws IOException {
        try {
            int numberOfIslands = 4;
            int bestMigrationRate = findBestMigrationRate(numberOfIslands);
            System.out.println("Best Migration Rate: " + bestMigrationRate);
        } catch (InvalidConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
    }

    private static int findBestMigrationRate(int numberOfIslands) throws InvalidConfigurationException, IOException {
        double bestAverageFitness = Double.MIN_VALUE;
        int bestMigrationRate = 0;

        for (int migrationRate : MIGRATION_RATES) {
            double totalFitness = 0;

            for (int run = 0; run < NUMBER_OF_RUNS; run++) {
                IslandManager manager = new IslandManager(numberOfIslands, migrationRate);
                manager.initializeIslandsForTest();

                CyclicBarrier barrier = new CyclicBarrier(numberOfIslands);
                Island.setBarrier(barrier);

                manager.startIslands();
                manager.joinIslands();

                totalFitness += manager.getBestOverallFitness();
            }

            double averageFitness = totalFitness / NUMBER_OF_RUNS;
            System.out.println("Migration Rate: " + migrationRate + ", Average Fitness: " + averageFitness);

            if (averageFitness > bestAverageFitness) {
                bestAverageFitness = averageFitness;
                bestMigrationRate = migrationRate;
            }
        }

        return bestMigrationRate;
    }
}
