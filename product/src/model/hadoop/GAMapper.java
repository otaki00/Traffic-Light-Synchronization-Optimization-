package model.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import model.GA.GeneticAlgorithm;
import model.Gene.IntersectionGene;
import model.Fitnessfunction.MyFitnessFunction;

import org.jgap.IChromosome;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;
import model.Gene.custom.CustomMutationOperator;

public class GAMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private Configuration gaConf;

    protected void setup(Context context) throws IOException, InterruptedException {
        try {
            this.gaConf = new DefaultConfiguration();
            MyFitnessFunction fitnessFunction = new MyFitnessFunction(0.5, 0.3, 0.2);
            gaConf.setFitnessFunction(fitnessFunction);
            gaConf.setPopulationSize(100);

            // Setup genes for the chromosome
            Gene[] intersectionGenes = new Gene[5]; // Adjust the size as per your problem
            for (int j = 0; j < intersectionGenes.length; j++) {
                intersectionGenes[j] = new IntersectionGene(gaConf);
            }
            IChromosome sampleChromosome = new Chromosome(gaConf, intersectionGenes);
            gaConf.setSampleChromosome(sampleChromosome);

            // Add genetic operators
            CrossoverOperator crossoverOperator = new CrossoverOperator(gaConf, 0.85);
            gaConf.addGeneticOperator(crossoverOperator);
            MutationOperator mutationOperator = new CustomMutationOperator(gaConf);
            gaConf.addGeneticOperator(mutationOperator);

        } catch (InvalidConfigurationException e) {
            throw new IOException(e);
        }
    }

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Assuming the value Text contains CSV data that might be used to configure or
        // influence the GA run
        String[] signalTimingData = value.toString().split(","); // Assuming CSV input
        try {
            GeneticAlgorithm ga = new GeneticAlgorithm(gaConf); // Initialize GA with the configuration

            // Optionally use the input data to adjust the GA or chromosome settings
            configureGAFromInput(ga, signalTimingData);

            for(int i = 0; i < 30; i++) {
                ga.evolve(1); // Run the GA for 100 generations
            }

            IChromosome fittest = ga.getFittestChromosome(); // Get the best solution

            context.write(new Text("BestFitness"), new DoubleWritable(fittest.getFitnessValue()));
        } catch (InvalidConfigurationException e) {
            throw new IOException(e);
        }
    }

    // You might want to adjust the GA configuration or initial population based on
    // input data
    private void configureGAFromInput(GeneticAlgorithm ga, String[] data) throws InvalidConfigurationException {
    // Assuming data contains relevant parameters for GA configuration
    // Example: data might include specific weights for the fitness function or initial values for genes

    // Adjust weights for the fitness function if provided
    if (data.length >= 3) {
        double weight1 = Double.parseDouble(data[0]);
        double weight2 = Double.parseDouble(data[1]);
        double weight3 = Double.parseDouble(data[2]);

        MyFitnessFunction fitnessFunction = new MyFitnessFunction(weight1, weight2, weight3);
        ga.getConfiguration().setFitnessFunction(fitnessFunction);
    }

    // Example: Adjust initial gene values based on input
    // if (data.length >= 8) {  
    //     Gene[] genes = ga.getPopulation();
    //     for (int i = 0; i < genes.length; i++) {
    //         // Assuming that the input data for genes are in positions 3 to 7
    //         ((IntegerGene) genes[i]).setAllele(Integer.parseInt(data[3 + i]));
    //     }
    // }

    // // Example: Modify the mutation or crossover rate based on data characteristics
    // if (data.length > 8) {
    //     double mutationRate = Double.parseDouble(data[8]);
    //     MutationOperator mutationOperator = new CustomMutationOperator(ga.getConfiguration(), mutationRate);
    //     ga.getConfiguration().addGeneticOperator(mutationOperator);
    // }
}

}
