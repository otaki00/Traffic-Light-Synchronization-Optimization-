package model.Isolation;

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
import model.Fitnessfunction.PopulationOperations;
import model.GA.GeneticAlgorithm;
import model.Gene.IntersectionGene;
import model.Gene.custom.CustomMutationOperator;

public class SingleModel {

    private String logFilePath;
    
    public SingleModel(String logFilePath){
        this.logFilePath = logFilePath;
    }


    private GeneticAlgorithm defineGA() throws InvalidConfigurationException {
        Configuration.reset();
        Configuration conf = new DefaultConfiguration();
            FitnessFunction myFitnessFunction = new MyFitnessFunction(0.5, 0.3, 0.2);
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
