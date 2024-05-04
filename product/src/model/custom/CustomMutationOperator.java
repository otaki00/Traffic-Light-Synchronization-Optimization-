package model.custom;

import java.util.List;

import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;
import org.jgap.impl.MutationOperator;

import model.IntersectionGene;

public class CustomMutationOperator extends MutationOperator{

    public CustomMutationOperator(Configuration a_conf) throws InvalidConfigurationException {
        super(a_conf);
    }

    public CustomMutationOperator(Configuration config, int mutationRate) throws InvalidConfigurationException {
        super(config, mutationRate);
    }

@SuppressWarnings("unchecked")
@Override
    public void operate(final Population a_population, final List a_candidateChromosomes) {
        // Ensure that the operation can be performed on the population
        if (a_population == null || a_candidateChromosomes == null) {
            // Population or candidate chromosomes list is null, do not perform operation.
            return;
        }

        int size = Math.min(getConfiguration().getPopulationSize(), a_population.size());
        RandomGenerator generator = getConfiguration().getRandomGenerator();

        // Iterate over the population by index
        for (int i = 0; i < size; i++) {
            IChromosome chrom = a_population.getChromosome(i);
            IChromosome copyOfChromosome = (IChromosome) chrom.clone();
            for (int j = 0; j < copyOfChromosome.size(); j++) {
                Gene gene = copyOfChromosome.getGene(j);
                if (gene instanceof IntersectionGene) {
                    // Apply custom mutation logic
                    double mutationRate = 0.5; 
                    if (generator.nextDouble() <= mutationRate) {
                        ((IntersectionGene) gene).applyMutation(j, mutationRate);
                    }
                }
            }
            // Add the mutated chromosome candidate to the candidate list
            a_candidateChromosomes.add(copyOfChromosome);
        }
    }
}
