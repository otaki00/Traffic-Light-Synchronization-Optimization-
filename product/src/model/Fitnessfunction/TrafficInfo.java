package model.Fitnessfunction;

import java.io.Serializable;

import org.jgap.IChromosome;

public class TrafficInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final double SATURATION_FLOW_RATE = 7200;
    // this constant is about the arrival rate of vehicles to the intersection, so the value is based on average values
    private static final double ARRIVAL_RATE = 600;
    
    // just an expicted value for red light time and yellow light time, to gain more
    // accurate results we need to get these values from the real world
    private static final double YELLOW_AND_ALL_RED_TIME = 6;


    // function to calculate the average delay for the chromosome
    public double calculateAverageDelay(IChromosome chromosome) {
        double totalDelay = 0;

        // extract the values from the chromosome
        for (int i = 0; i < chromosome.size(); i++) {
            int[] signalTimings = (int[]) chromosome.getGene(i).getAllele();
            int greenTimeTotal = signalTimings[0] + signalTimings[1] + signalTimings[2];
            int cycleTime = signalTimings[3];

            // Use Webster's formula as a basis for delay calculation
            double effectiveGreenTime = greenTimeTotal - YELLOW_AND_ALL_RED_TIME;
            if (effectiveGreenTime < 0) {
                effectiveGreenTime = 0; // Avoid negative green time
            }
            double redTime = cycleTime - greenTimeTotal;
            // this formula is based on Traffic Flow 
            // Delay = 0.5 * (redTime)^2 / (cycleTime - effectiveGreenTime)
            double delay = (0.5 * redTime * redTime) / (cycleTime - effectiveGreenTime); 

            totalDelay += delay;
        }

        return totalDelay / chromosome.size(); // Return average delay
    }

    // this function to calculate the throughput for the chromosome
    public double calculateAverageThroughput(IChromosome chromosome) {
        double totalThroughput = 0;

        // extract the values from the chromosome
        for (int i = 0; i < chromosome.size(); i++) {
            int[] signalTimings = (int[]) chromosome.getGene(i).getAllele();
            int greenTimeTotal = signalTimings[0] + signalTimings[1] + signalTimings[2];
            int cycleTime = signalTimings[3];

            // this is part of the formula to calculate the throughput
            // greenRatio = greenTimeTotal / cycleTime
            double greenRatio = (double) greenTimeTotal / cycleTime;

            // this formula is based on Traffic Flow
            // Throughput = Saturation Flow Rate * greenRatio
            double throughput = SATURATION_FLOW_RATE * greenRatio; 

            totalThroughput += throughput;
        }

        return totalThroughput / chromosome.size();
    }


    // this function to calculate the average queue length for the chromosome
    public double calculateAverageQueueLength(IChromosome chromosome) {
        double totalQueueLength = 0;

        // Extract the values from the chromosome
        for (int i = 0; i < chromosome.size(); i++) {
            int[] signalTimings = (int[]) chromosome.getGene(i).getAllele();
            int greenTimeTotal = signalTimings[0] + signalTimings[1] + signalTimings[2];
            int cycleTime = signalTimings[3];

            // Calculate the queue length
            double redRatio = 1 - (double) greenTimeTotal / cycleTime;
            double queueLength = ARRIVAL_RATE * redRatio;

            totalQueueLength += queueLength;
        }

        // Ensure stability by adding a small constant to avoid division by zero
        return totalQueueLength / (chromosome.size() + 1e-6);
    }

}
