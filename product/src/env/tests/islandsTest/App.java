package env.tests.islandsTest;

import java.util.concurrent.CyclicBarrier;

import org.jgap.*;


import model.Island.Island;
import model.Island.IslandManager;

public class App {
    public static void main(String[] args) {
        try {
            int numberOfIslands = 50;
            long startTime = System.currentTimeMillis();
            IslandManager manager = new IslandManager(numberOfIslands);
            manager.initializeIslands();

            CyclicBarrier barrier = new CyclicBarrier(numberOfIslands,
                    () -> System.out.println("All islands have reached the exchange point."));
            Island.setBarrier(barrier);

            manager.startIslands();
            manager.joinIslands();
            long endTime = System.currentTimeMillis();
            System.out.println("Total time: " + (endTime - startTime) + "ms");
        } catch (InvalidConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
    }

}
