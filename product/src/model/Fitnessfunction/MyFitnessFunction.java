package model.Fitnessfunction;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class MyFitnessFunction extends FitnessFunction {
    private final double w1, w2, w3;
    // initialize the traffic info object
    private TrafficInfo trafficInfo;

    public MyFitnessFunction(double weight1, double weight2, double weight3) {
        this.w1 = weight1;
        this.w2 = weight2;
        this.w3 = weight3;
        // inject the traffic info object
        this.trafficInfo = new TrafficInfo();
    }

    @Override
    protected double evaluate(IChromosome chromosome) {

        double fitness = calculateFitness(chromosome);
        return fitness > 0 ? fitness : 0; // Ensure fitness doesn't go negative
    }

    private double calculateFitness(IChromosome chromosome) {
        double averageDelay = this.trafficInfo.calculateAverageDelay(chromosome);
        double throughput = this.trafficInfo.calculateAverageThroughput(chromosome);
        double averageQueueLength = this.trafficInfo.calculateAverageQueueLength(chromosome);

        // Make sure averageDelay is not zero to avoid division by zero
        if (averageDelay <= 0) {
            averageDelay = 1;
        }

        // Calculate the fitness value according to the provided formula
        double fitness = (w1 * (1 / averageDelay)) + (w2 * throughput) - (w3 * averageQueueLength);

        return fitness;
    }
    
}
