package model;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class MyFitnessFunction extends FitnessFunction {
    private final double w1, w2, w3;

    public MyFitnessFunction(double weight1, double weight2, double weight3) {
        this.w1 = weight1;
        this.w2 = weight2;
        this.w3 = weight3;
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
        // You need to implement methods to calculate these values based on the
        // chromosome
        double averageDelay = calculateAverageDelay(chromosome);
        double throughput = calculateThroughput(chromosome);
        double averageQueueLength = calculateAverageQueueLength(chromosome);

        // Make sure averageDelay is not zero to avoid division by zero
        if (averageDelay <= 0) {
            throw new IllegalStateException("Average Delay must be positive to avoid division by zero.");
        }

        // Calculate the fitness value according to the provided formula
        double fitness = w1 * (1 / averageDelay) + w2 * throughput - w3 * averageQueueLength;

        return fitness;
    }

    // Placeholder method for calculating the average delay from the chromosome
    private double calculateAverageDelay(IChromosome chromosome) {
        // TODO: Implement the logic to calculate average delay based on the chromosome

        return 0; // This is just a placeholder, replace it with actual calculation
    }

    // Placeholder method for calculating the throughput from the chromosome
    private double calculateThroughput(IChromosome chromosome) {
        // TODO: Implement the logic to calculate throughput based on the chromosome
        return 0; // This is just a placeholder, replace it with actual calculation
    }

    // Placeholder method for calculating the average queue length from the
    // chromosome
    private double calculateAverageQueueLength(IChromosome chromosome) {
        // TODO: Implement the logic to calculate average queue length based on the
        // chromosome
        return 0; // This is just a placeholder, replace it with actual calculation
    }

    
}
