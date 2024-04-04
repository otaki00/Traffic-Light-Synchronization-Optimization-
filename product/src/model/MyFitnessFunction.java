package model;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
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
        // You need to implement methods to calculate these values based on the
        // chromosome
        double averageDelay = this.trafficInfo.calculateAverageDelay(chromosome);
        double throughput = this.trafficInfo.calculateAverageThroughput(chromosome);
        double averageQueueLength = this.trafficInfo.calculateAverageQueueLength(chromosome);

        // Make sure averageDelay is not zero to avoid division by zero
        if (averageDelay <= 0) {
            averageDelay = 1;
        }

        // Calculate the fitness value according to the provided formula
        double fitness = w1 * (1 / averageDelay) + w2 * throughput - w3 * averageQueueLength;

        return fitness;
    }

    // Placeholder method for calculating the average delay from the chromosome
    // private double calculateAverageDelay(IChromosome chromosome) {
    //     // TODO: Implement the logic to calculate average delay based on the chromosome
    //     // first we need to loop over all intersections get the green light timing and cycle length
    //     // then we need to calculate the delay for each intersection
    //     // finally we need to calculate the average delay for all intersections

    //     int totalDelay = 0;

    //     for (int i = 0; i < chromosome.size(); i++) {
    //         IntersectionGene intersection = (IntersectionGene) chromosome.getGene(i);
    //         int[] values = (int[]) intersection.getAllele();
            
    //         int overAllGreenLightTime = values[0] + values[1] + values[2];
    //         int cycleLength = values[3];

    //         int delayForIntersection = cycleLength - overAllGreenLightTime;

    //         totalDelay += delayForIntersection;
    //     }


    //     return totalDelay / chromosome.size(); 
    // }


//     private double calculateThroughputForIntersection(Gene intersectionGene) {
//     int[] gene = (int[]) intersectionGene.getAllele();
//     int grTimingSet1 = gene[0];
//     int grTimingSet2 = gene[1];
//     int grTimingSet3 = gene[2];
//     int cycleLength = gene[3];
//     // average lanes for any road
//     int numberOfLanes = 2;

//     // Calculate throughput for each set of movement
//     double throughputSet1 = (SATURATION_FLOW_RATE * grTimingSet1 / cycleLength) * numberOfLanes;
//     double throughputSet2 = (SATURATION_FLOW_RATE * grTimingSet2 / cycleLength) * numberOfLanes;
//     double throughputSet3 = (SATURATION_FLOW_RATE * grTimingSet3 / cycleLength) * numberOfLanes;

//     // Sum throughputs for all sets to get total throughput at the intersection
//     double totalThroughput = throughputSet1 + throughputSet2 + throughputSet3;

//     return totalThroughput;
// }

    // // Placeholder method for calculating the throughput from the chromosome
    // private double calculateThroughput(IChromosome chromosome) {
    //     // TODO: Implement the logic to calculate throughput based on the chromosome

    //     double totalThroughput = 0;
    //     for (int i = 0; i < chromosome.size(); i++) {
    //         Gene currentGene = chromosome.getGene(i);
    //         totalThroughput += calculateThroughputForIntersection(currentGene);
    //     }
    //     return totalThroughput / chromosome.size();
    // }

    // // Placeholder method for calculating the average queue length from the
    // // chromosome
    // private double calculateAverageQueueLength(IChromosome chromosome) {
    //     // TODO: Implement the logic to calculate average queue length based on the
    //     // chromosome
    //     return 0; // This is just a placeholder, replace it with actual calculation
    // }

    
}
