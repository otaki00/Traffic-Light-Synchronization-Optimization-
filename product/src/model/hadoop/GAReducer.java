package model.hadoop;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class GAReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {
        double sum = 0;
        int count = 0;
        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }
        context.write(key, new DoubleWritable(sum / count)); // Output average fitness
    }
}
