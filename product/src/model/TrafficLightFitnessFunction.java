package model;

public class TrafficLightFitnessFunction {

    // Weights for each component of the fitness function
    private double w1, w2, w3, w4;

    // Constructor to initialize weights
    public TrafficLightFitnessFunction(double w1, double w2, double w3, double w4) {
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
    }

    // Method to calculate the fitness of a traffic light configuration
    public double calculateFitness(int currentGreenTimeA, int[] vehicleQueues, int[] previousGreenTimes,
            int[] avgArrivals, int[] adjacentGreenTimes) {
        double E1 = calculateE1(currentGreenTimeA, vehicleQueues);
        double E2 = calculateE2(currentGreenTimeA, avgArrivals);
        double E3 = calculateE3(currentGreenTimeA, adjacentGreenTimes, previousGreenTimes);
        double E4 = calculateE4(currentGreenTimeA, adjacentGreenTimes, previousGreenTimes);

        return w1 * E1 + w2 * E2 + w3 * E3 + w4 * E4;
    }

    // Calculate E1 based on current green time and vehicle queues
    private double calculateE1(int currentGreenTime, int[] vehicleQueues) {
        int queueRight = vehicleQueues[0]; // Vehicle queue on the right
        int queueLeft = vehicleQueues[1]; // Vehicle queue on the left
        double TFP = 1.0; // Time for one vehicle to pass

        return Math.abs(currentGreenTime - queueRight * TFP) - Math.abs(currentGreenTime - queueLeft * TFP);
    }

    // Calculate E2 based on current green time and average arrivals
    private double calculateE2(int currentGreenTime, int[] avgArrivals) {
        int avgUp = avgArrivals[0]; // Average arrival rate of vehicles from up direction
        int avgDown = avgArrivals[1]; // Average arrival rate of vehicles from down direction

        return (currentGreenTime * avgUp) + (currentGreenTime * avgDown);
    }

    // Calculate E3 for adjacent intersections
    private double calculateE3(int currentGreenTime, int[] adjacentGreenTimes, int[] previousGreenTimes) {
        int greenTimeB = adjacentGreenTimes[0];
        int greenTimeC = adjacentGreenTimes[1];
        int previousGreenB = previousGreenTimes[0];
        int previousGreenC = previousGreenTimes[1];

        return Math.abs(greenTimeB - currentGreenTime) + Math.abs(greenTimeB - previousGreenB) +
                Math.abs(greenTimeC - currentGreenTime) + Math.abs(greenTimeC - previousGreenC);
    }

    // Calculate E4 for intersections two blocks away
    private double calculateE4(int currentGreenTime, int[] distantGreenTimes, int[] previousGreenTimes) {
        int greenTimeD = distantGreenTimes[0];
        int greenTimeE = distantGreenTimes[1];
        int previousGreenD = previousGreenTimes[2];
        int previousGreenE = previousGreenTimes[3];

        return Math.abs(greenTimeD - currentGreenTime) + Math.abs(greenTimeD - previousGreenD) +
                Math.abs(greenTimeE - currentGreenTime) + Math.abs(greenTimeE - previousGreenE);
    }
}
