package env.tests.sequintialRuning;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;
import org.jgap.InvalidConfigurationException;

import model.Isolation.SingleModel;

public class Test {
    public static void main(String[] args) throws IOException {
        int[] NumberOfNodes = { 10, 50, 100 };
        String outputFilePath = "data/seq/results.csv";

        // Create the output CSV file and write the header
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("Node,TimeTaken(ms)\n");
        }

        try {
            for (int node : NumberOfNodes) {
                long startTime = System.currentTimeMillis();
                for (int i = 0; i < node; i++) {
                    String path = "data/seq/model" + i + ".csv";
                    SingleModel singleModel = new SingleModel(path);

                    // Record the start time

                    // Run the model
                    singleModel.run();
                    TimeUnit.SECONDS.sleep((long) 1.5);
                    }
                    long endTime = System.currentTimeMillis();
                    long timeTaken = endTime - startTime;
                    String result = node + "," + timeTaken + "\n";
                    Files.write(Paths.get(outputFilePath), result.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (InvalidConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Interrupted error: " + e.getMessage());
        }
    }
}
