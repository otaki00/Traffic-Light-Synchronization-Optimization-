package env.tests.migrationTest;


import java.io.*;
import java.net.*;
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
    private ServerSocket serverSocket;
    private int port;
    private int migrationRate;

    private double bestFitness;

    public Island(GeneticAlgorithm ga, int islandId, String logFilePath, int port, int migrationRate) {
        this.ga = ga;
        this.islandId = islandId;
        this.logFilePath = logFilePath;
        this.port = port;
        this.migrationRate = migrationRate;
    }

    public static void setBarrier(CyclicBarrier newBarrier) {
        barrier = newBarrier;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            serverSocket = new ServerSocket(port);
            writer.write("Generation,BestFitness,GR Timing Set1,GR Timing Set2,GR Timing Set3,Cycle Length,Offset\n");

            for (int i = 0; i < 30; i++) {
                ga.evolve(1);
                bestFitness = ga.getFittestChromosome().getFitnessValue();
                IChromosome bestChromosome = ga.getFittestChromosome();

                for (int j = 0; j < bestChromosome.size(); j++) {
                    int[] geneValues = (int[]) bestChromosome.getGene(j).getAllele();
                    writer.write(i + "," + bestFitness + "," + geneValues[0] + "," + geneValues[1] + ","
                            + geneValues[2] + "," + geneValues[3] + "," + geneValues[4] + "\n");
                }

                if (i % migrationRate == 0 && i != 0) {
                    exchangeIndividuals();
                }

                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public double getBestFitness() {
        return bestFitness;
    }

    private void exchangeIndividuals() {
        exchangePool.offer(ga.getFittestChromosome());

        // Start a new thread to handle the exchange
        new Thread(() -> {
            try {
                Socket clientSocket = new Socket("localhost", getAnotherIslandPort());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                // Send the fittest chromosome
                out.writeObject(ga.getFittestChromosome());

                // Receive the new chromosome
                IChromosome newcomer = (IChromosome) in.readObject();
                if (newcomer != null) {
                    ga.integrateNewcomer(newcomer);
                }

                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();

        // Accept incoming connections
        try {
            Socket socket = serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send the fittest chromosome
            out.writeObject(ga.getFittestChromosome());

            // Receive the new chromosome
            IChromosome newcomer = (IChromosome) in.readObject();
            if (newcomer != null) {
                ga.integrateNewcomer(newcomer);
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int getAnotherIslandPort() {
        // Logic to get another island's port
        return 5000 + (islandId + 1) % 4;
    }
}
