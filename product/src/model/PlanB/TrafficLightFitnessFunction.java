package model.PlanB;

import org.jgap.*;

public class TrafficLightFitnessFunction extends FitnessFunction {
    private final int[][] queueLengths;
    private final int[][] averageArrivalRates;
    private final int[] previousGreenTimes;

    public TrafficLightFitnessFunction(int[][] queueLengths, int[][] averageArrivalRates, int[] previousGreenTimes) {
        this.queueLengths = queueLengths;
        this.averageArrivalRates = averageArrivalRates;
        this.previousGreenTimes = previousGreenTimes;
    }

    @Override
    protected double evaluate(IChromosome a_chromosome) {
        int greenTimeA = (int) a_chromosome.getGene(0).getAllele();
        int greenTimeB = (int) a_chromosome.getGene(1).getAllele();
        int greenTimeC = (int) a_chromosome.getGene(2).getAllele();
        int greenTimeD = (int) a_chromosome.getGene(3).getAllele();
        int greenTimeE = (int) a_chromosome.getGene(4).getAllele();

        double nonFitness = calculateNonFitness(greenTimeA, greenTimeB, greenTimeC, greenTimeD, greenTimeE);

        // Ensure fitness value is positive
        double fitness = Math.max(1.0 / (1.0 + nonFitness), Double.MIN_VALUE);

        // Debugging: Print the values being evaluated
        System.out.println("Evaluating chromosome: ");
        System.out.println("Green time A: " + greenTimeA);
        System.out.println("Green time B: " + greenTimeB);
        System.out.println("Green time C: " + greenTimeC);
        System.out.println("Green time D: " + greenTimeD);
        System.out.println("Green time E: " + greenTimeE);
        System.out.println("Non-fitness value: " + nonFitness);
        System.out.println("Fitness value: " + fitness);

        return fitness;
    }

    private double calculateNonFitness(int greenTimeA, int greenTimeB, int greenTimeC, int greenTimeD, int greenTimeE) {
        double E1 = calculateE1(greenTimeA);
        double E2 = calculateE2(greenTimeA);
        double E3 = calculateE3(greenTimeA, greenTimeB, greenTimeC);
        double E4 = calculateE4(greenTimeA, greenTimeD, greenTimeE);

        double nonFitness = 0.25 * E1 + 0.25 * E2 + 0.25 * E3 + 0.25 * E4; // Example weights

        // Debugging: Print the intermediate values
        System.out.println("E1: " + E1);
        System.out.println("E2: " + E2);
        System.out.println("E3: " + E3);
        System.out.println("E4: " + E4);
        System.out.println("Non-fitness (weighted sum): " + nonFitness);

        return nonFitness;
    }

    private double calculateE1(int greenTimeA) {
        int QrA = queueLengths[0][0];
        int QlA = queueLengths[0][1];
        int TFP = 1; // Example time required by a vehicle to pass an intersection

        double E1 = Math.abs(greenTimeA - QrA * TFP) - Math.abs(greenTimeA - QlA * TFP);

        // Debugging: Print the E1 calculation details
        System.out.println("Calculating E1: QrA=" + QrA + ", QlA=" + QlA + ", greenTimeA=" + greenTimeA + ", E1=" + E1);

        return E1;
    }

    private double calculateE2(int greenTimeA) {
        int AVGup = averageArrivalRates[0][0];
        int AVGdown = averageArrivalRates[0][1];

        double E2 = (greenTimeA * AVGup) + (greenTimeA * AVGdown);

        // Debugging: Print the E2 calculation details
        System.out.println("Calculating E2: AVGup=" + AVGup + ", AVGdown=" + AVGdown + ", greenTimeA=" + greenTimeA
                + ", E2=" + E2);

        return E2;
    }

    private double calculateE3(int greenTimeA, int greenTimeB, int greenTimeC) {
        double E3 = Math.abs(greenTimeB - greenTimeA) + Math.abs(greenTimeC - greenTimeA)
                + Math.abs(greenTimeB - greenTimeC);

        // Debugging: Print the E3 calculation details
        System.out.println("Calculating E3: greenTimeA=" + greenTimeA + ", greenTimeB=" + greenTimeB + ", greenTimeC="
                + greenTimeC + ", E3=" + E3);

        return E3;
    }

    private double calculateE4(int greenTimeA, int greenTimeD, int greenTimeE) {
        double E4 = Math.abs(greenTimeD - greenTimeA) + Math.abs(greenTimeE - greenTimeA)
                + Math.abs(greenTimeD - greenTimeE);

        // Debugging: Print the E4 calculation details
        System.out.println("Calculating E4: greenTimeA=" + greenTimeA + ", greenTimeD=" + greenTimeD + ", greenTimeE="
                + greenTimeE + ", E4=" + E4);

        return E4;
    }
}
