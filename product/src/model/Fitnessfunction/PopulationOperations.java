package model.Fitnessfunction;

import org.jgap.Population;

public class PopulationOperations {

    public static double calculateAverageFitness(Population population) {
    double totalFitness = 0;
    int count = population.size();
    for (int i = 0; i < count; i++) {
        totalFitness += population.getChromosome(i).getFitnessValueDirectly(); // Use getFitnessValueDirectly() to avoid recalculating fitness
    }
    return (count > 0) ? (totalFitness / count) : 0;
}

public static double calculateWorstFitness(Population population) {
    if (population.size() == 0) return 0;

    double worstFitness = Double.MAX_VALUE;
    for (int i = 0; i < population.size(); i++) {
        double fitness = population.getChromosome(i).getFitnessValueDirectly();
        if (fitness < worstFitness) {
            worstFitness = fitness;
        }
    }
    return worstFitness;
}

}
