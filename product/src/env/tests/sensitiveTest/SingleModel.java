package env.tests.sensitiveTest;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;

import model.Fitnessfunction.MyFitnessFunction;
import model.GA.GeneticAlgorithm;
import model.Gene.IntersectionGene;
import model.Gene.custom.CustomMutationOperator;

public class SingleModel {

    private String logFilePath;
    private double w1;
    private double w2;
    private double w3;

    public SingleModel(String logFilePath, double w1, double w2, double w3) {
        this.logFilePath = logFilePath;
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
    }

    private GeneticAlgorithm defineGA() throws InvalidConfigurationException {
        Configuration.reset(); 
        Configuration conf = new DefaultConfiguration();
        FitnessFunction myFitnessFunction = new MyFitnessFunction(w1, w2, w3);
        conf.setFitnessFunction(myFitnessFunction);
        conf.setPopulationSize(100);

        Gene[] intersectionGenes = new Gene[5];
        for (int j = 0; j < intersectionGenes.length; j++) {
            intersectionGenes[j] = new IntersectionGene(conf);
        }

        IChromosome sampleChromosome = new Chromosome(conf, intersectionGenes);
        conf.setSampleChromosome(sampleChromosome);

        CrossoverOperator crossoverOperator = new CrossoverOperator(conf, 80);
        conf.addGeneticOperator(crossoverOperator);

        MutationOperator mutationOperator = new CustomMutationOperator(conf);
        conf.addGeneticOperator(mutationOperator);

        GeneticAlgorithm ga = new GeneticAlgorithm(conf);

        return ga;
    }

    public void run() throws InvalidConfigurationException, IOException {
        GeneticAlgorithm ga = defineGA();
        ga.savePerformanceData(logFilePath, 30);
    }
}
