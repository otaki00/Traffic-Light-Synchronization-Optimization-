package model.PlanB;

import org.jgap.*;
import org.jgap.impl.*;

public class TrafficLightOptimization {
    public static void main(String[] args) throws Exception {
        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(false);

        int[][] queueLengths = {
                { 5, 10 }, // Intersection A
                { 3, 7 }, // Intersection B
                { 8, 12 }, // Intersection C
                { 4, 6 }, // Intersection D
                { 7, 9 } // Intersection E
        };

        int[][] averageArrivalRates = {
                { 3, 5 }, // Intersection A
                { 2, 4 }, // Intersection B
                { 4, 6 }, // Intersection C
                { 3, 5 }, // Intersection D
                { 4, 7 } // Intersection E
        };

        int[] previousGreenTimes = { 43, 50, 20, 30, 40 }; // Example previous green times for intersections

        FitnessFunction myFunc = new TrafficLightFitnessFunction(queueLengths, averageArrivalRates, previousGreenTimes);
        conf.setFitnessFunction(myFunc);

        Gene[] sampleGenes = new Gene[5];
        for (int i = 0; i < sampleGenes.length; i++) {
            sampleGenes[i] = new TrafficLightBGene(conf);
            sampleGenes[i].setAllele(30); // Initializing genes to a meaningful green time value
        }

        IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(100);

        conf.getGeneticOperators().clear(); // Clear existing operators

        // Add a CrossoverOperator with a probability of 85%
        CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 0.85);
        conf.addGeneticOperator(crossoverOperator);

        // Add a MutationOperator with a probability of 50%
        MutationOperator mutationOperator = new MutationOperator(conf, 5);
        conf.addGeneticOperator(mutationOperator);

        Genotype population = Genotype.randomInitialGenotype(conf);

        for (int i = 0; i < population.getPopulation().size(); i++) {
            IChromosome chrom = population.getPopulation().getChromosome(i);
            for (int j = 0; j < chrom.size(); j++) {
                chrom.getGene(j).setAllele((int) (Math.random() * 50) + 10); // Random values between 10 and 60
            }
        }

        for (int i = 0; i < 30; i++) {
            population.evolve();
        }

        IChromosome bestSolutionSoFar = population.getFittestChromosome();
        System.out.println("Best solution has fitness " + bestSolutionSoFar.getFitnessValue());
        for (int i = 0; i < bestSolutionSoFar.size(); i++) {
            System.out.println("Green time for intersection " + (char) ('A' + i) + ": "
                    + bestSolutionSoFar.getGene(i).getAllele());
        }
    }
}
