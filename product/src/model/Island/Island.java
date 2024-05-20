package model.Island;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

import org.jgap.Gene;
import org.jgap.IChromosome;

import model.GA.GeneticAlgorithm;

public class Island implements Runnable {
    private GeneticAlgorithm ga;
    private static final ConcurrentLinkedQueue<IChromosome> exchangePool = new ConcurrentLinkedQueue<>();
    private static CyclicBarrier barrier;
    private int islandId;
    private String logFilePath;

    public Island(GeneticAlgorithm ga, int islandId, String logFilePath) {
        this.ga = ga;
        this.islandId = islandId;
        this.logFilePath = logFilePath;

    }

    

    public static void setBarrier(CyclicBarrier newBarrier) {
        barrier = newBarrier;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write("Generation,BestFitness,GR Timing Set1,GR Timing Set2,GR Timing Set3,Cycle Length,Offset\n");
            for (int i = 0; i < 30; i++) { // Assume 50 generations before exchange
                    ga.evolve(1);
                    double bestFitness = ga.getFittestChromosome().getFitnessValue();
                    IChromosome bestChromosome = ga.getFittestChromosome();

                    // int[] geneValues = (int[]) bestChromosome.getGene(0).getAllele();

                    for (int j = 0; j < bestChromosome.size(); j++) {
                        int[] geneValues = (int[]) bestChromosome.getGene(j).getAllele();
                        writer.write(i + "," + bestFitness + "," + geneValues[0] + "," + geneValues[1] + ","
                                + geneValues[2] + "," + geneValues[3] + "," + geneValues[4] + "\n");
                    }
                    // Thread.sleep(100); // Slow down for simulation purposes
                
                if (i % 10 == 0) { 
                    exchangeIndividuals();
                }
                try {
                    barrier.await(); // Wait for all islands to reach this point
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void exchangeIndividuals() {
        
        exchangePool.offer(ga.getFittestChromosome());

        // Try to fetch a new individual from the pool
        IChromosome newcomer = exchangePool.poll();
        if (newcomer != null) {
            ga.integrateNewcomer(newcomer);
        }
    }

    
}
