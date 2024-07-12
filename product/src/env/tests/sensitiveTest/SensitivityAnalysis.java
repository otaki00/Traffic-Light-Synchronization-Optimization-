package env.tests.sensitiveTest;

import java.io.IOException;
import org.jgap.InvalidConfigurationException;

public class SensitivityAnalysis {

    public static void main(String[] args) throws InvalidConfigurationException, IOException {
        double[] weightVariations = { 0.0, 0.3, 0.5, 0.7, 1.0 };

        for (double w1 : weightVariations) {
            if (w1 + 0 + 0 <= 1.0) {
                String logFilePath = "data/performance_w1_" + w1 + ".csv";
                for (double w2 : weightVariations) {
                    for (double w3 : weightVariations) {
                        if (w1 + w2 + w3 == 1.0) {
                            SingleModel model = new SingleModel(logFilePath, w1, w2, w3);
                            model.run();
                        }
                    }
                }
            }
        }

        for (double w2 : weightVariations) {
            if (0 + w2 + 0 <= 1.0) {
                String logFilePath = "data/performance_w2_" + w2 + ".csv";
                for (double w1 : weightVariations) {
                    for (double w3 : weightVariations) {
                        if (w1 + w2 + w3 == 1.0) {
                            SingleModel model = new SingleModel(logFilePath, w1, w2, w3);
                            model.run();
                        }
                    }
                }
            }
        }

        for (double w3 : weightVariations) {
            if (0 + 0 + w3 <= 1.0) {
                String logFilePath = "data/performance_w3_" + w3 + ".csv";
                for (double w1 : weightVariations) {
                    for (double w2 : weightVariations) {
                        if (w1 + w2 + w3 == 1.0) {
                            SingleModel model = new SingleModel(logFilePath, w1, w2, w3);
                            model.run();
                        }
                    }
                }
            }
        }
    }
}
